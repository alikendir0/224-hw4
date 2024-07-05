import java.util.*;

class HW4_solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    FileRead fr = new FileRead(sc.nextLine());
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

    String input = sc.nextLine();
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