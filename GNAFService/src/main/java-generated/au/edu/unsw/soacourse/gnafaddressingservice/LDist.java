package au.edu.unsw.soacourse.gnafaddressingservice;

/**
 * Created by Dhruv on 3/09/2016. Asi1
 */
public class LDist implements Comparable{
    public Integer score;
    public String SearchString;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSearchString() {
        return SearchString;
    }

    public void setSearchString(String searchString) {
        SearchString = searchString;
    }



    @Override
    public String toString() {
        return "LDist{" +
                "score=" + score +
                ", SearchString='" + SearchString + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        int result = ((LDist) o).getScore();
        return this.score - result;
    }
}
