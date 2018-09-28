package com.yugimaster.javhub.sql;

import com.google.gson.Gson;
import com.yugimaster.javhub.bean.HighPronMovie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Util {

    private static String sqlJson;

    private static final String REMOTE_IP = "96.45.179.179";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + ":3306/highporn";
    private static final String USER = "root";
    private static final String PASSWORD = "052816";

    public static Connection openConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            conn = null;
        } catch (SQLException e) {
            conn = null;
        }

        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            } finally {
                conn = null;
            }
        }
    }

    public static void query(String sql) {
        Connection conn = openConnection(URL, USER, PASSWORD);
        if (conn == null) {
            return;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            saveListData(result);
            result.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return;
    }

    public static boolean execSQL(Connection conn, String sql) {
        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }

        return execResult;
    }

    public static String getSqlJson() {
        return sqlJson;
    }

    private static void saveListData(ResultSet resultSet) {
        try {
            ArrayList<HighPronMovie> highPronMovies = new ArrayList<>();

            while (resultSet.next()) {
                HighPronMovie highPronMovie = new HighPronMovie();
                highPronMovie.setMovieId(resultSet.getInt("idMovie"));
                highPronMovie.setProductId(resultSet.getString("idProduct"));
                highPronMovie.setNameProduct(resultSet.getString("nameProduct"));
                highPronMovie.setTitle(resultSet.getString("strTitle"));
                highPronMovie.setPosterUrl(resultSet.getString("posterUrl"));
                highPronMovie.setRate(resultSet.getString("strRate"));
                highPronMovie.setYear(resultSet.getInt("Year"));
                highPronMovie.setVidLists(resultSet.getString("strVid"));
                highPronMovie.setPublishDate(resultSet.getString("publishYear"));
                highPronMovie.setAddedTime(resultSet.getString("addedDate"));
                highPronMovie.setActors(resultSet.getString("actors"));
                highPronMovie.setTags(resultSet.getString("strCategories"));
                highPronMovies.add(highPronMovie);
            }
            setMovieJson(highPronMovies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setMovieJson(ArrayList<HighPronMovie> highPronMovies) {
        LinkedHashMap<String, Object> jsonMap = new LinkedHashMap<String, Object>();
        LinkedHashMap<String, Object> videosMap = new LinkedHashMap<String, Object>();
        LinkedList<LinkedHashMap<String, Object>> videoList = new LinkedList<LinkedHashMap<String, Object>>();
        for (int i = 0; i < highPronMovies.size(); i++) {
            HighPronMovie highPronMovie = highPronMovies.get(i);
            LinkedHashMap<String, Object> videoMap = new LinkedHashMap<String, Object>();
            videoMap.put("MovieId", highPronMovie.getMovieId());
            videoMap.put("ProductId", highPronMovie.getProductId());
            videoMap.put("ProductName", highPronMovie.getNameProduct());
            videoMap.put("Title", highPronMovie.getTitle());
            videoMap.put("PosterUrl", highPronMovie.getPosterUrl());
            videoMap.put("Rate", highPronMovie.getRate());
            videoMap.put("Year", highPronMovie.getYear());
            videoMap.put("Vids", highPronMovie.getVidLists());
            videoMap.put("PublishDate", highPronMovie.getPublishDate());
            videoMap.put("AddedTime", highPronMovie.getAddedTime());
            videoMap.put("Actors", highPronMovie.getActors());
            videoMap.put("Tags", highPronMovie.getTags());
            videoList.add(videoMap);
        }
        videosMap.put("videos", videoList);
        videosMap.put("total", highPronMovies.size());
        jsonMap.put("data", videosMap);

        setSqlJson(jsonMap);
    }

    private static void setSqlJson(LinkedHashMap<String, Object> linkedHashMap) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(linkedHashMap);
        sqlJson = jsonString;
    }
}
