package com.example.blockchainmessaging;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class Block {
    public int difficulty;
    public long timestamp;
    public int index;
    String data,previousHash,Hash; int nonce;

    public Block(int index,String previousHash,long timestamp,String data,int difficulty) {
        this.timestamp = timestamp;
        this.index = index;
        this.data = data;
        this.previousHash = previousHash;
        nonce=0;
        this.difficulty =difficulty;
        this.Hash = Block.calculateHash_detail(this);

    }

    public int getDifficulty() {
        return difficulty;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    public static String calculateHash_detail(Block blockModel) {
        if (blockModel != null) {
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
            String txt = blockModel.str();
            final byte[] bytes = messageDigest.digest(txt.getBytes());
            StringBuilder builder = new StringBuilder();
            for (final byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) builder.append('0');
                builder.append(hex);
            }
            return builder.toString();
        }
        return null;
    }


    private  String calculateHash() {
        return Encrypt.getSHA256(this.previousHash+this+this.timestamp+this.data+this.nonce);
    }

    public String calculateHashForBlock(){
        return calculateHash();
    }


    public String getHash() {
        return this.Hash;
    }
    public void mineBlock(int difficulty) {

        while (!this.getHash().substring(0, difficulty).equals(addZeros(difficulty))) {
            nonce++;
            Hash = calculateHash_detail(this);
        }
    }
    private String addZeros(int difficulty) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < difficulty; i++) builder.append('0');
        return builder.toString();
    }
    private String str() {
        return index + timestamp + previousHash + data + nonce;
    }
}
