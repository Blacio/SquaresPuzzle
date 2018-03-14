package com.blacio.squarespuzzle;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

 class Functions {

    private long t;

     private int[] a;
     private int[] v;
     private int[] nb;
     private int sw;
     private int dim;
     private int dim2;

    private Button b;
    private TableRow row;
    private TableLayout tbl;
     private MediaPlayer m;
     private AudioManager audio;

     Functions(TableLayout tbl, int dim, Context context){

        sw=0;
         this.tbl=tbl;
         this.dim=dim;
         dim2=dim*dim;

         a = new int[100];

         v = new int[100];
         for (int i = 0; i < 100; i++) v[i] = 0;

        nb = new int[dim-3];

         m = MediaPlayer.create(context,R.raw.wrong);
         audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }



     long calculate_score(long s){

        int count=0;
        long dif;

         dif=s;


         for(int i:v)
             if(i>=1){
                 s++;
                 count++;
             }

         if(dim==10){
             s-=33;
             if(count>90)
                 s+=100;
                 s-=100*sw;
         }

         else if(dim==7){
             s-=16;
             if(count>43)
                 s+=49;
                 s-=49*sw;
         }

         else {
             s-=6;
             if (count>21)
                 s+=25;
                 s-=25*sw;
         }


         if(s>dif)
         s = dif + (s-dif)/((System.currentTimeMillis()-t)/60000+1);


        return s;
    }

    private void setUnvisited() {

        for (int i = 0; i < dim; i++) {
            row = (TableRow) tbl.getChildAt(i);
            for (int j = 0; j < dim; j++) {
                b = (Button) row.getChildAt(j);
                b.setBackgroundResource(R.drawable.button_unvisited);
                v[i * dim + j] = 0;
            }
        }
    }


     void set_startBackground(boolean bo) {


        if (bo) {
            setUnvisited();

            Random r = new Random();

            for (int i=0;i<nb.length;i++)
                nb[i] = r.nextInt(dim2);

            Joc joc = new Joc(dim2);
            a = joc.get_a();
        } else
            setUnvisited();

        for (int i:nb) {
            v[i] = 1;
            set_background(i, true);
        }

        t = System.currentTimeMillis();
         sw=0;
    }



     void doAction(int center) {


        if (v[center] >= 1) {

            int nord = center - dim;
            int east = center + 1;
            int south = center + dim;
            int west = center - 1;

            if (center == 0) {
                v[east]++;
                v[south]++;
                set_background(east, safe_east(center));
                set_background(south, safe_south(center));
            } else if (center == dim-1) {
                v[south]++;
                v[west]++;
                set_background(south, safe_south(center));
                set_background(west, safe_west(center));
            } else if (center == dim2-dim) {
                v[nord]++;
                v[east]++;
                set_background(nord, safe_nord(center));
                set_background(east, safe_east(center));
            } else if (center == dim2-1) {
                v[nord]++;
                v[west]++;
                set_background(nord, safe_nord(center));
                set_background(west, safe_west(center));
            } else if (center > 0 && center < dim-1) {
                v[east]++;
                v[south]++;
                v[west]++;
                set_background(east, safe_east(center));
                set_background(south, safe_south(center));
                set_background(west, safe_west(center));
            } else if (center > dim2-dim && center < dim2-1) {
                v[nord]++;
                v[east]++;
                v[west]++;
                set_background(nord, safe_nord(center));
                set_background(east, safe_east(center));
                set_background(west, safe_west(center));
            } else if (center % dim == 0) {
                v[nord]++;
                v[east]++;
                v[south]++;
                set_background(nord, safe_nord(center));
                set_background(south, safe_south(center));
                set_background(east, safe_east(center));
            } else if (center % dim == dim-1) {
                v[nord]++;
                v[south]++;
                v[west]++;
                set_background(nord, safe_nord(center));
                set_background(west, safe_west(center));
                set_background(south, safe_south(center));
            } else {
                v[nord]++;
                v[east]++;
                v[south]++;
                v[west]++;
                set_background(nord, safe_nord(center));
                set_background(east, safe_east(center));
                set_background(south, safe_south(center));
                set_background(west, safe_west(center));
            }
        }
    }

    private void set_background(int tag, boolean safe) {

        int i = tag / dim;
        int j = tag % dim;

        row = (TableRow) tbl.getChildAt(i);
        b = (Button) row.getChildAt(j);

        if (v[tag] == 1) {

            switch (a[tag]) {
                case 8888: {
                    b.setBackgroundResource(R.drawable.buttonshape);
                    break;
                }
                case 8881: {
                    b.setBackgroundResource(R.drawable.button_west);
                    break;
                }
                case 8818: {
                    b.setBackgroundResource(R.drawable.button_south);
                    break;
                }
                case 8811: {
                    b.setBackgroundResource(R.drawable.button_south_west);
                    break;
                }
                case 8188: {
                    b.setBackgroundResource(R.drawable.button_east);
                    break;
                }
                case 8181: {
                    b.setBackgroundResource(R.drawable.button_east_west);
                    break;
                }
                case 8118: {
                    b.setBackgroundResource(R.drawable.button_east_south);
                    break;
                }
                case 8111: {
                    b.setBackgroundResource(R.drawable.button_east_south_west);
                    break;
                }
                case 1888: {
                    b.setBackgroundResource(R.drawable.button_nord);
                    break;
                }
                case 1881: {
                    b.setBackgroundResource(R.drawable.button_nord_west);
                    break;
                }
                case 1818: {
                    b.setBackgroundResource(R.drawable.button_nord_south);
                    break;
                }
                case 1811: {
                    b.setBackgroundResource(R.drawable.button_nord_south_west);
                    break;
                }
                case 1188: {
                    b.setBackgroundResource(R.drawable.button_nord_east);
                    break;
                }
                case 1181: {
                    b.setBackgroundResource(R.drawable.button_nord_east_west);
                    break;
                }
                case 1118: {
                    b.setBackgroundResource(R.drawable.button_nord_east_south);
                    break;
                }
                case 1111: {
                    b.setBackgroundResource(R.drawable.button_full);
                    break;
                }
                default:
                    break;
            }
        } else if (!safe) {b.setBackgroundResource(R.drawable.button_visited);sw++;
            if (audio.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)
                m.start();
        }
    }


    private boolean safe_nord(int tag) {
        return (a[tag] / 1000 == 1);
    }

    private boolean safe_south(int tag) {
        return (a[tag] % 100 / 10 == 1);
    }

    private boolean safe_east(int tag) {
        return (a[tag] % 1000 / 100 == 1);
    }

    private boolean safe_west(int tag) {
        return (a[tag] % 10 == 1);
    }

     long getT(){
        return t;
    }
     void setT(long t){this.t+=t;}

}
