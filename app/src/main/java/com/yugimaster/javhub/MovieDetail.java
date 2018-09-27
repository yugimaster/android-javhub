package com.yugimaster.javhub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.yugimaster.javhub.common.Common;
import com.yugimaster.javhub.common.UIUtil;
import com.yugimaster.javhub.view.GridViewInScrollView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MovieDetail extends Activity {

    private final float ASPECT_RATIO_4_3 = 4f / 3f;
    private final String HOME_HOST = "http://sherwoodbp.com";
    private TextView movie_title, movie_cate, movie_actors, movie_desc, product_id;
    private ImageView movie_poster;
    private GridViewInScrollView buttonGridView;
//    private String url, poster, title, category, actors, desc;
    private ArrayList<HashMap<String, String>> btnList = new ArrayList<HashMap<String, String>>();

    private static final String PLAY_HOST = "http://play.openhub.tv";
    private static final String PLAY_API = "/playurl";
    private static final String TAG = "JavHub";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = getIntent().getExtras();
        String link = bundle.getString("link");

        buttonGridView = (GridViewInScrollView) findViewById(R.id.play_button_list);
        movie_title = (TextView)findViewById(R.id.movie_title);
        movie_cate = (TextView)findViewById(R.id.movie_category);
        movie_actors = (TextView)findViewById(R.id.movie_actors);
        movie_desc = (TextView)findViewById(R.id.movie_desc);
        product_id = (TextView)findViewById(R.id.product_id);
        movie_poster = (ImageView)findViewById(R.id.movie_poster);
        set_aspect_ratio(movie_poster, ASPECT_RATIO_4_3);
//        url = HOME_HOST + link;

        // Start a thread
//        new Thread(runnable).start();
        setMovieInfo(bundle);
    }

    private void setMovieInfo(Bundle bundle) {
        String posterUrl = bundle.getString("poster");
        String title = bundle.getString("title");
        String productId = bundle.getString("productId");
        String desc = bundle.getString("desc");
        String actors = bundle.getString("actresses");
        String tags = bundle.getString("tags");
        String playLists = bundle.getString("playLists");

        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.no_poster)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(movie_poster);
        movie_title.setText(title);
        product_id.setText(productId);
        movie_cate.setText(tags);
        movie_actors.setText(actors);
        movie_desc.setText(desc);

        String[] playList = playLists.split("/");
        for (int i = 1; i <= playList.length; i++) {
            String name = "Part " + i;
            String vid = String.valueOf(playList[i - 1]);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", name);
            map.put("vid", vid);
            btnList.add(map);
        }
        init_play_btns();
    }

    public void playUrlWithAndroidPlayer(String url) {
        Uri uri = Uri.parse(url);
        // use system player
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.e("URI:", uri.toString());
        intent.setDataAndType(uri, "video/*");
        startActivity(intent);
    }

//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            Connection conn = Jsoup.connect(url);
//            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
//            Document doc = null;
//            try {
//                doc = conn.get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Get movie info
//            Elements element_info = doc.select("[class=film_info clearfix]");
//            Element movie_info = element_info.first();
////            poster = movie_info.select("dd:eq(0)").select("img").attr("src");
////            title = movie_info.select("dd:eq(1)").select("span").text();
////            category = movie_info.select("dd:eq(2)").text();
////            actors = movie_info.select("dd:eq(3)").text();
////            desc = movie_info.select("dd:eq(4)").select("span").text();
//
//            // Get play button list
//            Elements element_bar = doc.select("[class=film_bar clearfix]");
//            for (Element div : element_bar) {
//                String name = div.select("span").text();
//                String link = div.select("a").attr("href");
//                String btn_name = name.split(": ")[1];
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("name", btn_name);
//                map.put("link", link);
//                btnList.add(map);
//            }
//
//            // Send a message to handler after finish
//            handler.sendEmptyMessage(0);
//        }
//    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // Receive message and run handler
            switch (msg.what) {
                case 0:
                    init_movie_info();
                    init_play_btns();
                    break;
                case 1:
                    Bitmap bmp = (Bitmap)msg.obj;
                    movie_poster.setImageBitmap(bmp);
                    break;
            }
        }
    };

    public void init_movie_info() {
//        movie_title.setText(title);
//        movie_cate.setText(category);
//        movie_actors.setText(actors);
//        movie_desc.setText(desc);
        init_poster();
    }

    public void init_poster() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Bitmap bmp = getBitmap(poster);
//                Message msg = new Message();
//                msg.what = 1;
//                msg.obj = bmp;
//                handler.sendMessage(msg);
            }
        }).start();

    }

    private void init_play_btns() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(MovieDetail.this, btnList,
                R.layout.play_button,
                new String[]{"name"},
                new int[]{R.id.play_btn});
        buttonGridView.setAdapter(simpleAdapter);
        buttonGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map =
                        (HashMap<String, String>)buttonGridView.getItemAtPosition(position);
                String btn_name = map.get("name");
                String btn_vid = map.get("vid");
                if (btn_vid.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "视频ID为空！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Long nowTime = Common.getUTCTime();
                    playUrlPost(nowTime, btn_vid);
                }
            }
        });
    }

    private void playUrlPost(Long nowTime, String vid) {
        String url = PLAY_HOST + PLAY_API + "?random=" + Long.toString(nowTime);
        HttpParams httpParams = new HttpParams();
        httpParams.put("v", vid);
        httpParams.put("source_play", "highporn");
        OkGo.<String>post(url)
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        if (!Common.isNetUrl(result)) {
                            Toast.makeText(getApplicationContext(), "无效的播放地址！",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            playUrlWithAndroidPlayer(result);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getApplicationContext(), "获取数据失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        try {
            URL imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void set_aspect_ratio(ImageView imageView, float scale) {
        // Calculate the sum of left and right spacing
        int padding = 10;
        int spacePx = (int)(UIUtil.dp2px(this, padding) * 2);

        // Calculate the width of the image
        int imageWidth = UIUtil.getScreenWidth(this) - spacePx;

        // Calculate the height of the image based on the width
        int imageHeight = (int)(imageWidth / scale);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        params.topMargin = (int)UIUtil.dp2px(this, padding);
        // Set Left/Right spacing
        params.leftMargin = (int)UIUtil.dp2px(this, padding);
        params.rightMargin = (int)UIUtil.dp2px(this, padding);

        imageView.setLayoutParams(params);
    }
}
