package com.example.okul.deneme22;


        import java.util.ArrayList;
        import java.util.List;
        import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
        import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
        import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import  android.support.v4.app.ListFragment;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;

/**
 * Created by okul on 22.10.2016.
 */
public class ListViewActivity extends ListFragment implements OnRefreshListener {
                int i=0;
                private	PullToRefreshLayout mPullToRefreshLayout;
                ArrayAdapter<String> adapter;
                List<String> list;

                @Override
                public void onViewCreated(View view, Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                //Listview g�stermek ama�l�, ArrayList 'e Item'lar atad�k
                list=new ArrayList<String>();
                int no=1;
                for(int i=0;i<5;i++)
                {
                        list.add("Item No :"+no++);
                }

                ViewGroup viewGroup = (ViewGroup) view;

                //PullToRefreshLayout s�n�f�n� tan�mlad�k.PullToRefreshLayout s�n�f� refresh olay�n� yapan k�t�phanedir
                mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

                //PullToRefreshLayout s�n�f�yla ilgili ilgili ayarlar� yap�yoruz.
                ActionBarPullToRefresh.from(getActivity())
                        //Fragment  ViewGroup icine PullToRefreshLayout^'u dahil ettik
                        .insertLayoutInto(viewGroup)

                        .theseChildrenArePullable(android.R.id.list, android.R.id.empty)
                        .listener(this)
                        .setup(mPullToRefreshLayout);

        }

                @Override
                public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                //onViewCreated metodunun i�inde, ArrayList 'te  olu�turdgumuz listeyi ArrayAdapter'a atad�k.
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, list);
                setListAdapter(adapter);
                setListShownNoAnimation(true);//Listelemedeki Y�kleniyor animasyonunu pasif yapt�m
        }

                @Override
                public void onRefreshStarted(View view) {
                //Listeleme alan�n� �ekip b�rakt���n�zda ,listeleme alanan�nda Round progress bar(Yuvarlak y�kleniyor iconu)
                // g�rmek istiyosan�z, bu kodun yorum taglerini kald�rmal�s�n�z
                 setListShown(false);


                //Listeleme alan� �ekip b�rakt���n�zda, yeni verinin g�sterilmesini sa�layan kod b�l�m�

                new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                                try {
                                        //Loading i�leminden sonra  yeni bir data g�sterilmesi i�lemi icin ,listview Item eklendi
                                        Thread.sleep(5000);
                                        int itemNo=list.size();
                                        itemNo++;
                                        list.add("New Item No :"+itemNo);

                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                                super.onPostExecute(result);

                                adapter.notifyDataSetChanged();
                                // Notify PullToRefreshLayout s�n�f�n�n yenilenme i�leminin bitti�ini anlatan metodu set ettim
                                mPullToRefreshLayout.setRefreshComplete();

                                // �stte "setListShown(false)" kodunu aktif yapt�ysan�z,asag�daki kodunda yorum tag lerini kald�rmal�s�n�z

                  /* if (getView() != null) {
                        setListShown(true);
                     }*/
                        }
                }.execute();
        }

}
