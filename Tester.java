import java.util.*;

public class Tester {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String[] test = {
        "search",
        "autocomplete",
        "reverse",
    };
    String[] test2 = {
        "a",
        "can",
        "we",
        "download",
        "read" };
    FileRead fr = new FileRead("Input1.txt");
    for (int i = 0; i < test.length; i++) {

      TST<Integer> st = new TST<Integer>();
      st.createReversedTST();
      String line;
      while ((line = fr.Read()) != "No more lines.") {
        String[] words = line.toLowerCase().split(" ");
        for (String word : words) {
          if (st.get(word) == null) {
            st.put(word, 1);
          } else {
            st.put(word, st.get(word) + 1);
          }
        }
      }
      for (int j = 0; j < 5; j++) {
        String input = test[i % 5] + " " + test2[j % 5];
        System.out.println(input);
        String op = input.split(" ")[0];
        switch (op) {
          case "search":
            st.search(input.toLowerCase().split(" ")[1]);
            break;
          case "autocomplete":
            st.autoComplete(input.toLowerCase().split(" ")[1]);
            break;
          case "reverse":
            st.reverseAutoComplete(input.toLowerCase().split(" ")[1]);
            break;
          case "full":
            st.FullAutoComplete(input.toLowerCase().split(" ")[1], input.toLowerCase().split(" ")[2]);
            break;
          case "topk":
            st.findTopK(Integer.parseInt(input.split(" ")[1]));
            break;
          default:
            break;
        }
      }
    }
  }

}
