package com.example.bilibiliplayer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_view);

        // 初始化 Retrofit 并获取视频 URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bilibili.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BiliService service = retrofit.create(BiliService.class);
        service.getVideo("BV1uzbFeEESu").enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    String videoUrl = response.body().getData().getVideoUrl();
                    playVideo(videoUrl);
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                // 处理错误
            }
        });
    }

    private void playVideo(String videoUrl) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}
