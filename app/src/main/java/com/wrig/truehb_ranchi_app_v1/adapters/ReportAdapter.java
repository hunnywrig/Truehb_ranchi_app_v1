package com.wrig.truehb_ranchi_app_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;
import com.wrig.truehb_ranchi_app_v1.utils.DisplayDateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private Context mContext;
    private List<TestDetailsDatabaseModel> mDataList;

    public ReportAdapter(Context mContext, List<TestDetailsDatabaseModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_report_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final TestDetailsDatabaseModel model = mDataList.get(position);


       // holder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

        holder.tv_Name.setText(model.getClient_name());

        //holder.tv_date.setText(model.getTest_time_stamp());
        holder.tv_date.setText(DisplayDateUtils.getDiplayDateString(model.getTest_time_stamp()));

        holder.tv_hbValue.setText( model.getClient_hb_value()+" g/dl");

        switch (model.getClient_gender())
        {
            case 1:
            {
                holder.tv_genderValue.setText("M");
                break;
            }
            case 2:
            {
                holder.tv_genderValue.setText("F");
                break;
            }
            case 3: {
                holder.tv_genderValue.setText("T");
                break;
            }
            default:
            {
                holder.tv_genderValue.setText("NA");
                break;
            }

        }

        holder.tv_ageValue.setText("" + model.getClient_age());
        holder.tv_districtValue.setText(model.getDistrict());

        holder.tv_blockValue.setText(model.getBlock());

        holder.tv_centerValue.setText(model.getPhc_uhc_sc());


    }


    private String getDate(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy - h:m a");
        return sdf.format(d);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.tv_name)
        TextView tv_Name;

        @BindView(R.id.tv_hbValue)
        TextView tv_hbValue;

        @BindView(R.id.tv_genderValue)
        TextView tv_genderValue;

        @BindView(R.id.tv_date)
        TextView tv_date;

        @BindView(R.id.tv_ageValue)
        TextView tv_ageValue;

        @BindView(R.id.tv_districtValue)
        TextView tv_districtValue;

        @BindView(R.id.tv_blockValue)
        TextView tv_blockValue;

        @BindView(R.id.tv_centerValue)
        TextView tv_centerValue;



        @BindView(R.id.container)
        RelativeLayout container;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}

