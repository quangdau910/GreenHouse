package com.quangdau.greenhouse.Adapter.RecycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_history.ObjHistoryLoginData;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class HistoryLoginAdapter extends RecyclerView.Adapter<HistoryLoginAdapter.LoginVH> {
    ArrayList<ObjHistoryLoginData> loginData;
    Context context;
    public HistoryLoginAdapter(Context context, ArrayList<ObjHistoryLoginData> data) {
        this.loginData = data;
        this.context = context;
    }


    @NonNull
    @Override
    public LoginVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_history_login, parent, false);
        return new LoginVH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoginVH holder, int position) {
        ObjHistoryLoginData objHistoryLoginData = loginData.get(position);
        holder.deviceName.setText(""+ context.getResources().getString(R.string.device_name)+":" + objHistoryLoginData.getDevice_name());
        holder.account.setText(""+context.getResources().getString(R.string.account_history)+":"+ objHistoryLoginData.getAccount());
        holder.ip.setText("Ip: "+ objHistoryLoginData.getIp());
        holder.loginTime.setText(""+context.getResources().getString(R.string.login_time)+":" + formatDate(objHistoryLoginData.getLogin_time()));

        boolean isExpanded = loginData.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return loginData.size();
    }

    class LoginVH extends RecyclerView.ViewHolder{
        ConstraintLayout expandableLayout;
        TextView deviceName, account, ip, loginTime;

        public LoginVH(@NonNull View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.textViewDeviceNameHistoryLogin);
            account = itemView.findViewById(R.id.textViewAccountHistoryLogin);
            ip = itemView.findViewById(R.id.textViewIpHistoryLogin);
            expandableLayout = itemView.findViewById(R.id.expandableLayoutCardViewHistoryLogin);
            loginTime = itemView.findViewById(R.id.textViewLoginTimeHistoryLogin);

            deviceName.setOnClickListener(v -> {
                ObjHistoryLoginData objHistoryLoginData = loginData.get(getAdapterPosition());
                objHistoryLoginData.setExpanded(!objHistoryLoginData.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }


    private String formatDate(String date){
        OffsetDateTime dateTimeWithOffset = Instant.parse(date).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toOffsetDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss (dd/MM/yyyy)");
        return fmt.format(dateTimeWithOffset);
    }


}
