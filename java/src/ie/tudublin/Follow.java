package ie.tudublin;

/**
 * @author Ledion Pashaj | C21317311
 * @date 26/04/2023
 */

public class Follow {
    
    private String word; 
    private int count;

    public Follow() {
        this.word = "";
        this.count = 0;
    }

    public Follow(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }
}
