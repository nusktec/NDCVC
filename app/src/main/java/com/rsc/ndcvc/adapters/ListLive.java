package com.rsc.ndcvc.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rsc.ndcvc.R;
import com.rsc.ndcvc.databinding.ListLivesBinding;
import com.rsc.ndcvc.models.ModelLiveVideo;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.TwilioException;

import java.util.List;

public class ListLive extends RecyclerView.Adapter<ListLive.VH> {
    //declare model
    private List<ModelLiveVideo> videoLists;
    private onTouchLive onTouchLive;
    private destroyChild dest;
    private ListLivesBinding pbinding;

    //open constructor
    public ListLive(List<ModelLiveVideo> modelLiveVideos, onTouchLive onPress, destroyChild dest) {
        this.videoLists = modelLiveVideos;
        this.onTouchLive = onPress;
        this.dest = dest;
    }

    private ListLivesBinding listLivesBindingView;
    private int counter = -5;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listLivesBindingView = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_lives, parent, false);
        return new VH(listLivesBindingView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //initializing
        counter = position;
        ModelLiveVideo video = videoLists.get(position);
        holder.binding.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTouchLive.itemPressed(video, position);
            }
        });
        this.pbinding = holder.binding;
        video.getParticipants().setListener(plistener());
    }

    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    //create inner class
    static class VH extends RecyclerView.ViewHolder {
        ListLivesBinding binding;

        //make constructor
        VH(ListLivesBinding bind) {
            super(bind.getRoot());
            this.binding = bind;
        }
    }

    //interface for onclick
    public interface onTouchLive {
        void itemPressed(ModelLiveVideo liveVideo, int pos);
    }

    public interface destroyChild {
        void itemDest(int pos, String name);
    }

    //participant listener
    private RemoteParticipant.Listener plistener() {
        return new RemoteParticipant.Listener() {
            @Override
            public void onAudioTrackPublished(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onAudioTrackUnpublished(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onAudioTrackSubscribed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication, @NonNull RemoteAudioTrack remoteAudioTrack) {

            }

            @Override
            public void onAudioTrackSubscriptionFailed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication, @NonNull TwilioException twilioException) {

            }

            @Override
            public void onAudioTrackUnsubscribed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication, @NonNull RemoteAudioTrack remoteAudioTrack) {

            }

            @Override
            public void onVideoTrackPublished(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }

            @Override
            public void onVideoTrackUnpublished(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }

            @Override
            public void onVideoTrackSubscribed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication, @NonNull RemoteVideoTrack remoteVideoTrack) {
                //add to views
                remoteVideoTrack.addRenderer(listLivesBindingView.lvideo);
                listLivesBindingView.txtName.setText(remoteParticipant.getIdentity().split("\\|")[0].split("~")[0]);

            }

            @Override
            public void onVideoTrackSubscriptionFailed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication, @NonNull TwilioException twilioException) {
                try {
                    dest.itemDest(counter, remoteParticipant.getIdentity());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onVideoTrackUnsubscribed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication, @NonNull RemoteVideoTrack remoteVideoTrack) {
                try {
                    dest.itemDest(counter, remoteParticipant.getIdentity());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onDataTrackPublished(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteDataTrackPublication remoteDataTrackPublication) {

            }

            @Override
            public void onDataTrackUnpublished(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteDataTrackPublication remoteDataTrackPublication) {

            }

            @Override
            public void onDataTrackSubscribed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteDataTrackPublication remoteDataTrackPublication, @NonNull RemoteDataTrack remoteDataTrack) {

            }

            @Override
            public void onDataTrackSubscriptionFailed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteDataTrackPublication remoteDataTrackPublication, @NonNull TwilioException twilioException) {

            }

            @Override
            public void onDataTrackUnsubscribed(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteDataTrackPublication remoteDataTrackPublication, @NonNull RemoteDataTrack remoteDataTrack) {

            }

            @Override
            public void onAudioTrackEnabled(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication) {
                pbinding.micLive.setVisibility(View.VISIBLE);
                pbinding.cover.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAudioTrackDisabled(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteAudioTrackPublication remoteAudioTrackPublication) {
                pbinding.micLive.setVisibility(View.GONE);
                pbinding.cover.setCardBackgroundColor(Color.BLACK);
            }

            @Override
            public void onVideoTrackEnabled(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication) {
                //control video disabled

            }

            @Override
            public void onVideoTrackDisabled(@NonNull RemoteParticipant remoteParticipant, @NonNull RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }
        };
    }
}
