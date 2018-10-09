package com.yugimaster.javhub;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.base.activity.IBaseActivity;
import com.ljy.devring.image.support.LoadOption;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yugimaster.javhub.adapter.DividerItemDecoration;
import com.yugimaster.javhub.adapter.MyRecyclerViewAdapter;
import com.yugimaster.javhub.api.MovieList;
import com.yugimaster.javhub.bean.HighPronMovie;
import com.yugimaster.javhub.bean.JsonData;
import com.yugimaster.javhub.bean.JsonSqlResult;
import com.yugimaster.javhub.bean.MyFavMovies;
import com.yugimaster.javhub.bean.VideoItem;
import com.yugimaster.javhub.drawer.Item;
import com.yugimaster.javhub.drawer.MyMenuAdapter;
import com.yugimaster.javhub.sql.Util;
import com.yugimaster.javhub.view.CustomAdapter;
import com.yugimaster.javhub.view.RowItem;

import org.jsoup.helper.StringUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    private ArrayList<Item> menuItemList;
    private List<RowItem> rowItemList;
    private ListView listLeftDrawer;
    private ListView listView;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mRecycleViewAdapter;
    private MyMenuAdapter<Item> myMenuAdapter = null;
    private List<VideoItem> myFavVideoItems = null;
    private List<HighPronMovie> mySqlMovies = null;

    private ProgressDialog dialog;
    private SearchView searchView;

    private ImageView mIvAvatar;

    private String requestUrl;
    private String categoryLink;
    private String jsonMyFav;
    private String sqlQuery;
    private int currentStatus;

    private static final String HOST = "http://sherwoodbp.com";
    private static final String API_SEARCH = "/search/videos?search_query=";
    private static final String MY_JSON = "https://raw.githubusercontent.com/yugimaster/HubPlay/master/MyFavMovies.json";
    private static final String TAG = "Hub";

    private static final int INIT_HOME = 1;
    private static final int REFRESH_LIST = 2;
    private static final int INIT_MY_FAV = 3;
    private static final int INIT_SQL_MOVIE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Fresco
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        rowItemList = new ArrayList<RowItem>();

        initView();
        initEvent();

        requestUrl = HOST;
        categoryLink = "/";
        jsonMyFav = "";
        currentStatus = INIT_HOME;
        sqlQuery = "select * from movie";

        firstInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initEvent() {
        // Set DrawerLayout action toggle listener
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                View mContent = drawerLayout.getChildAt(0);
                mContent.setTranslationX(drawerView.getMeasuredWidth() * slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        // Set menu on click event in navigation view
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_item_fav:
                        break;
                    case R.id.nav_item_upload:
                        break;
                    case R.id.nav_item_download:
                        break;
                }

                return false; //true the menu item is selected, false is not
            }
        });

        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RingToast.show(R.string.github);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);
        setSearchView(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set search menu option
     * @param menu
     */
    private void setSearchView(Menu menu) {
        // find menu item and set SearchView
        MenuItem item = menu.getItem(0);
        searchView = new SearchView(this);
        item.setActionView(searchView);

        // set search background white
        item.collapseActionView();
        searchView.setQuery("", false); // set submit string but not submit
        searchView.setBackgroundResource(R.drawable.white);

        // set default expand
//        searchView.setIconified(false);
//        searchView.setIconifiedByDefault(false);
//        searchView.onActionViewExpanded();
        searchView.setQueryHint("请输入关键字");

        // use submit button
//        searchView.setSubmitButtonEnabled(true);

        // set search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Search query submit " + query,
                        Toast.LENGTH_SHORT).show();
                sqlQuery = String.format("select * from movie where CONCAT(idProduct,strTitle,strCategories) LIKE '%s'", "%" + query + "%");
                System.out.println("sql query: " + sqlQuery);
                new Thread(get_sql_query).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void firstInit() {
        if (isNetworkAvailable(MainActivity.this)) {
            showLoadingDialog();
            new Thread(get_sql_query).start();
        } else
            showTipsDialog();
    }

    private void getHtmlContent(String resUrl) {
        if (currentStatus == INIT_HOME)
            MovieList.main(resUrl);
        else {
            MovieList.categoryPage(resUrl, categoryLink);
        }
        rowItemList = MovieList.getRowItemList();
    }

    private void rowItemRefresh() {
        if (isNetworkAvailable(MainActivity.this)) {
            showLoadingDialog();
            clearListView();
            new Thread(parse_html).start();
        } else
            showTipsDialog();
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nv_menu);
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        mIvAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.iv_avatar);

        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mToolbar.setTitle("");
        this.setSupportActionBar(mToolbar);
        mToolbar.setTitle("Home");
        DevRing.imageManager().loadRes(R.mipmap.ic_avatar, mIvAvatar, new LoadOption().setIsCircle(true));
    }

    private void initMenuDrawer() {
        menuItemList = new ArrayList<Item>();
        menuItemList.add(new Item(R.drawable.menu_nofo, "中文无码", "/l/l40.html"));
        menuItemList.add(new Item(R.drawable.menu_nofo, "中文有码", "/l/l42.html"));
        menuItemList.add(new Item(R.drawable.menu_nofo, "1pondo", "/l/l39.html"));
        menuItemList.add(new Item(R.drawable.menu_nofo, "Caribbean", "/l/l41.html"));
        menuItemList.add(new Item(R.drawable.menu_nofo, "Chinese", "/l/l28.html"));
        menuItemList.add(new Item(R.drawable.menu_nofo, "HEYZO", "/l/l29.html"));
        myMenuAdapter = new MyMenuAdapter<Item>(menuItemList, R.layout.menu_list) {
            @Override
            public void bindView(ViewHolder holder, Item obj) {
                holder.setImageResource(R.id.img_icon, obj.getIconId());
                holder.setText(R.id.txt_content, obj.getIconName());
            }
        };
        listLeftDrawer.setAdapter(myMenuAdapter);
        listLeftDrawer.setOnItemClickListener(this);
    }

    private void initListView() {
        mRecycleViewAdapter = new MyRecyclerViewAdapter(rowItemList);
        // set add/del item animator, use default
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecycleViewAdapter);

        mRecycleViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RowItem rowItem) {
                String fanart = rowItem.getFanart();
                String title = rowItem.getTitle();
                String productId = rowItem.getProductId();
                String desc = rowItem.getDesc();
                String actresses = rowItem.getActresses();
                String tags= rowItem.getTags();
                String playLists = rowItem.getPlayLists();
                Intent intent = new Intent(MainActivity.this, MovieDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("fanart", fanart);
                bundle.putString("title", title);
                bundle.putString("productId", productId);
                bundle.putString("desc", desc);
                bundle.putString("actresses", actresses);
                bundle.putString("tags", tags);
                bundle.putString("playLists", playLists);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void clearListView() {
        rowItemList.clear();
        mRecyclerView.setAdapter(null);
    }

    private void initMyFavListView() {
        for (int i=0; i<myFavVideoItems.size(); i++) {
            VideoItem videoItem = myFavVideoItems.get(i);
            String imgUrl = videoItem.getPoster();
            String title = videoItem.getTitle();
            String productId = videoItem.getProductId();
            String desc = videoItem.getDesc();
            List<String> actressesEn= videoItem.getGetActressesEn();
            List<String> tags= videoItem.getTags();
            List<Long> playList = videoItem.getPlayList();
            String actresses = StringUtil.join(actressesEn, ",");
            String str_tags = StringUtil.join(tags, ",");
            String playLists = StringUtil.join(playList, "/");
            RowItem rowItem = new RowItem(imgUrl, title, productId, str_tags, actresses, productId,
                    desc, playLists, "");
            rowItemList.add(rowItem);
        }
        initListView();
    }

    private void initMySqlMovieListView() {
        for (int i = 0; i < mySqlMovies.size(); i++) {
            HighPronMovie movieItem = mySqlMovies.get(i);
            String posterUrl = movieItem.getPosterUrl();
            String title = movieItem.getTitle();
            String productId = movieItem.getProductId();
            String actors = movieItem.getActors();
            String tags = movieItem.getTags();
            String vids = movieItem.getVidLists();
            String fanart = movieItem.getFanart();
            if (actors == null)
                actors = "";
            actors = actors.replace("|", ", ");
            tags = tags.replace("|", ", ");
            vids = vids.replace("|", "/");
            RowItem rowItem = new RowItem(posterUrl, title, productId, tags, actors, productId, title, vids, fanart);
            rowItemList.add(rowItem);
        }
        initListView();
    }

    private void showLoadingDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载数据...");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("当前没有网络连接！")
                .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rowItemRefresh();
                    }
                }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        }).show();
    }

    // Check Internet is connected
    private boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    private void getMyFavMoviesJson() {
        OkGo.<String>get(MY_JSON)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        jsonMyFav = response.body();
                        Gson gson = new Gson();
                        MyFavMovies myFavMovies = gson.fromJson(jsonMyFav, MyFavMovies.class);
                        if (myFavMovies.getResult().getRet() == 0 && myFavMovies.getData() != null) {
                            Log.e(TAG, "get my favorite movies data success");
                            myFavVideoItems = myFavMovies.getData().getVideos();
                            currentStatus = INIT_MY_FAV;
                            clearListView();
                            handler.sendEmptyMessage(currentStatus);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e(TAG, "get my favorite movies data failed");
                    }
                });
    }

    Runnable parse_html = new Runnable() {
        @Override
        public void run() {
            getHtmlContent(requestUrl);
            handler.sendEmptyMessage(currentStatus);
        }
    };

    Runnable parse_myfav = new Runnable() {
        @Override
        public void run() {
            getMyFavMoviesJson();
        }
    };

    Runnable get_sql_query = new Runnable() {
        @Override
        public void run() {
            Util.query(sqlQuery);
            String movieSqlJson = Util.getSqlJson();
            System.out.println("Movie sql json: " + movieSqlJson);
            Gson gson = new Gson();
            JsonSqlResult mySqlResult = gson.fromJson(movieSqlJson, JsonSqlResult.class);
            mySqlMovies = mySqlResult.getData().getVideos();
            currentStatus = INIT_SQL_MOVIE;
            handler.sendEmptyMessage(currentStatus);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT_HOME:
                    initListView();
                    dialog.dismiss();
                    break;
                case REFRESH_LIST:
                    initListView();
                    dialog.dismiss();
                    break;
                case INIT_MY_FAV:
                    initMenuDrawer();
                    initMyFavListView();
                    dialog.dismiss();
                    break;
                case INIT_SQL_MOVIE:
                    clearListView();
                    initMySqlMovieListView();
                    dialog.dismiss();
                    break;
            }
        }
    };
}
