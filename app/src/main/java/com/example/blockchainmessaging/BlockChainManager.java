package com.example.blockchainmessaging;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class BlockChainManager {
    public  static ArrayList<Block> Blockchain;
    public static int difficulty;
    public Block getGenesisBlock(){
        return new Block(0, null, System.currentTimeMillis(), "my genesis block!!",difficulty);
    }

    public BlockChainManager(int difficulty, @NonNull Context context) {
        this.difficulty=difficulty;
        Blockchain=new ArrayList<>();
        Block block = getGenesisBlock();
        block.mineBlock(difficulty);
        Blockchain.add(block);
    }

    public Block generateNextBlock(String data){
        Block previousBlock = getLatestBlock();
        int nextIndex = previousBlock.index;
        long nextTimeStamp = new Date().getTime();// 1000;
        //String nextHash = calculateHash(nextIndex, previousBlock.Hash, nextTimeStamp, data);
        return new Block(nextIndex+1,previousBlock.Hash,nextTimeStamp,data,difficulty);
    }

    public Block getLatestBlock(){
        return (Blockchain.get(Blockchain.size() - 1));
    }



    public void addBlock(Block newBlock){
        if (isValidNewBlock(newBlock, getLatestBlock())) {
            {
                newBlock.mineBlock(difficulty);
                Blockchain.add(newBlock);
            }

        }
    }

//    public boolean isValidNewBlock(Block newBlock,Block prevBlock){
//        if (prevBlock.index + 1 != newBlock.index) {
////            console.log('invalid index');
//            Log.d("BLOCKCHAINMANAGER","Invalid Index1");
//            return false;
//        } else if (prevBlock.Hash != newBlock.previousHash) {
//            //console.log('invalid previoushash');
//            Log.d("BLOCKCHAINMANAGER","Invalid Index2");
//            return false;
//        } else if (newBlock.calculateHashForBlock() != newBlock.Hash) {
//            //console.log(typeof (newBlock.hash) + ' ' + typeof calculateHashForBlock(newBlock));
//            //console.log('invalid hash: ' + calculateHashForBlock(newBlock) + ' ' + newBlock.hash);
//            Log.d("BLOCKCHAINMANAGER","Invalid Index3");
//            return false;
//        }
//        return true;
//    }
    private boolean isValidNewBlock(@Nullable Block newBlock, @Nullable Block previousBlock)
    {
        if(newBlock!=null && previousBlock!=null)
        {
            if(previousBlock.getIndex()+1!=newBlock.getIndex()) return false;
            if(newBlock.getPreviousHash()==null && !newBlock.getPreviousHash().equals(newBlock.getData())) return false;
            return newBlock.getHash()!=null && Block.calculateHash_detail(newBlock).equals(newBlock.getHash());

        }
        return true;
    }


}
