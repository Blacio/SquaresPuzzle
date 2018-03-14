package com.blacio.squarespuzzle;

import java.util.Random;

 class Joc {

    private static int[] code_0_sus={8888,8881,8818,8811,8188,8181,8118,8111};
    private static int[] code_0_jos={8888,8881,8188,8181,1888,1881,1181,1188};
    private static int[] code_0_st= {8888,8881,8818,8811,1888,1881,1818,1811};
    private static int[] code_0_dr= {8888, 8818,8188,8118,1888,1818,1188,1118};

    private  int[]a;
    private  int x;
    private static int y;

    Joc(int x){

        this.x=x;
        y = (int) Math.sqrt((double)x);

        a = new int[x];
        int[] nr = new int[x];
        int nr_sum=x;

        Random r = new Random();

        for(int i =0;i<x;i++)
            a[i]=-1;

        for(int i=0;i<x;i++)
            nr[i]=i;

        while(nr_sum!=0) {

            int numar = r.nextInt(nr_sum);

            a[nr[numar]] = generate_nr(a, nr[numar]);

            for (int u = numar; u < nr_sum - 1; u++)
                nr[u] = nr[u + 1];

            nr_sum--;

        }
    }

    private static int generate_nr(int[]a,int i){

        Random rnd = new Random();

        int[] code ={8888,8881,8818,8811,8188,8181,8118,8111,1888,1881,1818,1811,1188,1181,1118,1111};

        int nr_code=16;

        try{
            if(verif(a[i+y],code_0_jos)){
                for(int o=0;o<nr_code;o++)
                    if(code[o]/1000!=1){
                        for(int k=o;k<nr_code-1;k++)
                            code[k]=code[k+1];
                        o--;
                        nr_code--;
                    }
            }
        }catch(Exception e){}


        try{
            if(verif(a[i-y],code_0_sus)){
                for(int o=0;o<nr_code;o++)
                    if(code[o]%100/10!=1){
                        for(int k=o;k<nr_code-1;k++)
                            code[k]=code[k+1];
                        o--;
                        nr_code--;
                    }
            }
        }catch(Exception e){}


        try{
            if(verif(a[i+1],code_0_dr)){
                for(int o=0;o<nr_code;o++)
                    if(code[o]%10!=1){
                        for(int k=o;k<nr_code-1;k++)
                            code[k]=code[k+1];
                        o--;
                        nr_code--;
                    }
            }
        }catch(Exception e){}


        try{
            if(verif(a[i-1],code_0_st)){
                for(int o=0;o<nr_code;o++)
                    if(code[o]%1000/100!=1){
                        for(int k=o;k<nr_code-1;k++)
                            code[k]=code[k+1];
                        o--;
                        nr_code--;
                    }
            }
        }catch(Exception e){}

        return code[rnd.nextInt(nr_code)];

    }

    private static boolean verif(int q,int[] cod){
        for(int i:cod)
            if(q==i)
                return true;
        return false;
    }

     int[] get_a(){
        return a;
    }
}

