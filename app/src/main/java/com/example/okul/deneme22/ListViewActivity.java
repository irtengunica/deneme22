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
                //Listview göstermek amaçlý, ArrayList 'e Item'lar atadýk
                list=new ArrayList<String>();
                int no=1;
                for(int i=0;i<5;i++)
                {
                        list.add("Item No :"+no++);
                }

                ViewGroup viewGroup = (ViewGroup) view;

                //PullToRefreshLayout sýnýfýný tanýmladýk.PullToRefreshLayout sýnýfý refresh olayýný yapan kütüphanedir
                mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

                //PullToRefreshLayout sýnýfýyla ilgili ilgili ayarlarý yapýyoruz.
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
                //onViewCreated metodunun içinde, ArrayList 'te  oluþturdgumuz listeyi ArrayAdapter'a atadýk.
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, list);
                setListAdapter(adapter);
                setListShownNoAnimation(true);//Listelemedeki Yükleniyor animasyonunu pasif yaptým
        }

                @Override
                public void onRefreshStarted(View view) {
                //Listeleme alanýný çekip býraktýðýnýzda ,listeleme alananýnda Round progress bar(Yuvarlak yükleniyor iconu)
                // görmek istiyosanýz, bu kodun yorum taglerini kaldýrmalýsýnýz
                 setListShown(false);


                //Listeleme alaný çekip býraktýðýnýzda, yeni verinin gösterilmesini saðlayan kod bölümü

                new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                                try {
                                        //Loading iþleminden sonra  yeni bir data gösterilmesi iþlemi icin ,listview Item eklendi
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
                                // Notify PullToRefreshLayout sýnýfýnýn yenilenme iþleminin bittiðini anlatan metodu set ettim
                                mPullToRefreshLayout.setRefreshComplete();

                                // Üstte "setListShown(false)" kodunu aktif yaptýysanýz,asagýdaki kodunda yorum tag lerini kaldýrmalýsýnýz

                  /* if (getView() != null) {
                        setListShown(true);
                     }*/
                        }
                }.execute();
        }

}
