package ie.tudublin;

import java.util.ArrayList;

/**
 * @author Ledion Pashaj | C21317311
 * @date 26/04/2023
 */

public class Word {

    private String word;
    private ArrayList<Follow> follows;

    public Word(String word) {
        this.word = word;
        this.follows = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Follow> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<Follow> follows) {
        this.follows = follows;
    }

    public void addFollow(Follow follow) {
        this.follows.add(follow);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word{word='").append(word).append("', follows=[");
        for (int i = 0; i < follows.size(); i++) {
            sb.append(follows.get(i).toString());
            if (i < follows.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public Follow findFollow(String str) {
        for (Follow follow : follows) {
            if (follow.getWord().equals(str)) {
                return follow;
            }
        }
        return null;
    }

}