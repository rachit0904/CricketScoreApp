package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class SquadParentAdapter extends RecyclerView.Adapter<SquadParentAdapter.ViewHolder> {
    List<SquadModel> squadModelList;

    public SquadParentAdapter(List<SquadModel> squadModelList) {
        this.squadModelList = squadModelList;
    }

    @NonNull
    @Override
    public SquadParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.squad_rv_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SquadParentAdapter.ViewHolder holder, int position) {
        SquadModel model=squadModelList.get(position);
        holder.SquadName.setText(model.getSquadName());
        //TODO - set squad logo
        List<SeriesModel> playerNamesList=new ArrayList<>();
        List<SeriesModel> playerNamesList2=new ArrayList<>();
        int i;
        for(i=1;i<=9;i++) {
            SeriesModel model1 = new SeriesModel("Player "+i);
            playerNamesList.add(model1);
        }
        for(int j=1;j<8;j++) {
            SeriesModel model1 = new SeriesModel("Player "+i);
            playerNamesList2.add(model1);
            i++;
        }
        holder.rv1.hasFixedSize();
        holder.rv1.setLayoutManager(new LinearLayoutManager(holder.rv1.getContext()));
        holder.adapter1=new SquadRvChildAdapter(playerNamesList);
        holder.rv1.setAdapter(holder.adapter1);
        holder.rv2.hasFixedSize();
        holder.rv2.setLayoutManager(new LinearLayoutManager(holder.rv2.getContext()));
        holder.adapter2=new SquadRvChildAdapter(playerNamesList2);
        holder.rv2.setAdapter(holder.adapter2);
    }

    @Override
    public int getItemCount() {
        return squadModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView squadLogo,collapseBtn;
        TextView SquadName;
        View divider;
        LinearLayout layout;
        RecyclerView rv1,rv2;
        List<SeriesModel> list,list2;
        Boolean flag=false;
        RecyclerView.Adapter adapter1,adapter2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            squadLogo=itemView.findViewById(R.id.squadIcon);
            collapseBtn=itemView.findViewById(R.id.viewSwitch);
            SquadName=itemView.findViewById(R.id.squadName);
            divider=itemView.findViewById(R.id.squadRvDivider);
            layout=itemView.findViewById(R.id.squadMemeberLayout);
            rv1=itemView.findViewById(R.id.membersListRv1);
            rv2=itemView.findViewById(R.id.membersListRv2);
            collapseBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==collapseBtn){
                if(flag==false) {
                    collapseBtn.setBackground(v.getResources().getDrawable(R.drawable.showless));
                    layout.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.VISIBLE);
                    flag=true;
                }else{
                    collapseBtn.setBackground(v.getResources().getDrawable(R.drawable.showmore));
                    layout.setVisibility(View.GONE);
                    divider.setVisibility(View.GONE);
                    flag=false;
                }
            }
        }
    }
}
