package com.example.blockchainmessaging;

import android.content.Context;
import android.renderscript.RSDriverException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class BlockChainAdapter extends RecyclerView.Adapter<BlockChainAdapter.BlockViewHolder> {
    private ArrayList<Block> blockChain;
    private Context mcontext;
    public BlockChainAdapter(ArrayList<Block> blockChain, Context context) {
        this.blockChain = blockChain;
        this.mInflater = LayoutInflater.from(context);
        this.mcontext=context;
    }

    private LayoutInflater mInflater;

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.blockviewholder_item,parent,false);
        return new BlockViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        holder.txtIndex.setText(String.format(mcontext.getString(R.string.title_block_number),blockChain.get(position).getIndex()));
        holder.txtIndex.setText(String.format(mcontext.getString(R.string.title_block_number),blockChain.get(position).getIndex()));
        holder.txtPreviousHash.setText(blockChain.get(position).getPreviousHash()!=null ? blockChain.get(position).getPreviousHash() : "Null");

        holder.txtTimestamp.setText(String.valueOf(new Date(blockChain.get(position).getTimestamp())));
        holder.txtData.setText(blockChain.get(position).getData());
        holder.txtHash.setText(blockChain.get(position).getHash());

        if(position%4==0){
            holder.parent.setBackgroundResource(R.drawable.bg2);

        }else if(position%4==1){
            holder.parent.setBackgroundResource(R.drawable.bg1);
        }else if(position%4==2){
            holder.parent.setBackgroundResource(R.drawable.bg3);
        }
        else{
            holder.parent.setBackgroundResource(R.drawable.bg4);
        }



    }


    @Override
    public int getItemViewType(int position)
    {
        return R.layout.blockviewholder_item;
    }
    @Override
    public int getItemCount() {
        return blockChain.size();
    }
     class BlockViewHolder extends RecyclerView.ViewHolder{
//        public TextView BlockItemView;
//        final BlockChainAdapter mAdapter;
//
//        public BlockViewHolder(@NonNull View  blockItemView, BlockChainAdapter mAdapter) {
//            super(blockItemView);
//            this.BlockItemView = blockItemView.findViewById(R.id.block_item);
//            this.mAdapter = mAdapter;
////            ItemView.setOnClickListener(this::onClick);
//        }
         public TextView txtIndex,txtPreviousHash,txtTimestamp,txtData,txtHash;
         final BlockChainAdapter mAdapter;
         public LinearLayout parent;

         public BlockViewHolder(@NonNull View itemView,BlockChainAdapter Adapter) {
             super(itemView);
             txtIndex= itemView.findViewById(R.id.txt_index);
             txtPreviousHash = itemView.findViewById(R.id.txt_previous_hash);
             txtTimestamp = itemView.findViewById(R.id.txt_timestamp);
             txtData = itemView.findViewById(R.id.txt_data);
             txtHash = itemView.findViewById(R.id.txt_hash);
             this.mAdapter=Adapter;
             parent = itemView.findViewById(R.id.card_sublayout);
         }


    }
}
/*public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>{

    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;



    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View ItemView, WordListAdapter adapter) {
            super(ItemView);
            this.wordItemView = ItemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            ItemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            String element = mWordList.get(position);
            mWordList.set(position,element+" Clicked!");
            mAdapter.notifyDataSetChanged();
        }
    }

    public WordListAdapter(Context context,LinkedList<String> wordList){
        mInflater = LayoutInflater.from(context);
        this.mWordList=wordList;
    }



    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item,parent,false);
        return new WordViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position).toString();
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }



}
*/