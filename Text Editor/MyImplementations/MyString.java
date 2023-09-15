package MyImplementations;

// Implementing the String class
public class MyString {
  private char[] chars;

  // Constructor from a char array
  public MyString(char[] chars) {
    this.chars = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      this.chars[i] = chars[i];
    }
  }

  // constructor from a String
  public MyString(String s) {
    this(s.toCharArray());
  }

  // constructor from a MyString
  public MyString(MyString s) {
    this(s.toCharArray());
  }

  // toCharArray
  public char[] toCharArray() {
    char[] result = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      result[i] = chars[i];
    }
    return result;
  }

  // toString
  public String toString() {
    return new String(chars);
  }

  // Returns the length of the string
  public int length() {
    // WRITE YOUR CODE HERE
    return chars.length;
  }

  // Returns the character at the specified index
  public char charAt(int index) {
    // WRITE YOUR CODE HERE
    return chars[index];
  }

  // Returns a new MyString that is a substring of this MyString
  public MyString substring(int begin, int end) {
    // WRITE YOUR CODE HERE
    int len = end - begin;
    char[] result = new char[len];
    System.arraycopy(chars, begin, result, 0,len);
    return new MyString(result);
  }

  // Returns the MyString that is the concatenation of this MyString and the
  // specified MyString
  public MyString concat(MyString s) {
    // WRITE YOUR CODE HERE
    int len = this.length() + s.length();
    char[] result = new char[len];
    System.arraycopy(chars, 0, result, 0, this.length());
    int j = 0;
    for (int i = this.length(); i < len; i++){
      result[i] = s.charAt(j++);
    }
//        System.arraycopy(s,0, result, chars.length, s.length());
    return new MyString(result);
  }

  // Returns the MyString that is the concatenation of this MyString and the String
  public MyString concat(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.concat(s1);
  }

  // indexOf
  public int indexOf(MyString s) {
    // WRITE YOUR CODE HERE
    return this.indexOf(s,0);
  }

  // indexOf
  public int indexOf(MyString s, int fromIndex) {
    // WRITE YOUR CODE HERE
    int m = s.length();
    int n = this.length();
    int index = -1;

    for (int i = 0; i <= n-m; i++){
      int j;
      for (j = 0; j < m; j++){
        if (this.charAt(i+j) != s.charAt(j)){
          break;
        }
      }
      if (j == m && index == -1){
        index = i;
        j = 0;
      }
    }

    return index;
  }

  // replace oldString with newString
  public MyString replace(MyString oldString, MyString newString) {
    // WRITE YOUR CODE HERE
    for(int i = 0; i < this.length() - oldString.length()+1; i++)
    {
      if(this.charAt(i) == oldString.charAt(0) && this.substring(i, i + oldString.length()).equals(oldString))
      {
        int replacedStringLength = this.length() - oldString.length() + newString.length();
        char[] replaced = new char[replacedStringLength];
        // Copy the current String up to the index of oldString
        for(int j = 0; j < i; j++)
        {
          replaced[j] = this.charAt(j);
        }
        // Copy the newString into the array directly after the initial
        for(int j = i; j < i + newString.length(); j++)
        {
          replaced[j] = newString.charAt(j-i);
        }
        // Copy the rest of the current String
        int startPos = i + oldString.length();
        for(int j = i + newString.length(); j < replacedStringLength; j++)
        {
          replaced[j] = this.charAt(j - i + newString.length() + startPos);
        }
        return new MyString(replaced);
      }
    }
    return this;
  }

  public MyString replace(String oldString, String newString) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(oldString);
    MyString s2 = new MyString(newString);
    return this.replace(s1,s2);
  }

  public MyString replace(char oldChar, char newChar) {
    // WRITE YOUR CODE HERE
    char[] result = new char[this.length()];
    for (int i= 0; i < this.length(); i++){
      result[i] = this.charAt(i);
      if (this.charAt(i) == oldChar){
        result[i] = newChar;
      }
    }
    return new MyString(result);
  }

  // count
  public int count(MyString s) {
    // WRITE YOUR CODE HERE
    int m = s.length();
    int n = this.length();
    int counter = 0;

    for (int i = 0; i <= n-m; i++){
      int j;
      for (j = 0; j < m; j++){
        if (this.charAt(i+j) != s.charAt(j)){
          break;
        }
      }
      if (j == m){
        counter++;
        j = 0;
      }
    }

    return counter;
  }

  // count
  public int count(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.count(s1);
  }

  // count
  public int count(char c) {
    // WRITE YOUR CODE HERE
    int counter = 0;
    for (int i = 0; i < this.length(); i++){
      if (this.charAt(i) == c){
        counter++;
      }
    }
    return counter;
  }

  // split
  public MyString[] split(MyString regex) {
    // WRITE YOUR CODE HERE
    int counter = this.count(regex);
    int m = regex.length();
    int n = this.length();
    int[] start = new int[counter];
    int[] end = new int[counter];
    int a = 0; int b =0;

    for (int i = 0; i <= n-m; i++) {
      int j;
      for (j = 0; j < m; j++) {
        if (this.charAt(i + j) != regex.charAt(j)) {
          break;
        }
      }
      if (j == m) {
        start[a++] =i;
        end[b++] = i + regex.length();
        j = 0;
      }
    }

    counter += 1;
    if (this.startsWith(regex) || this.endsWith(regex)){
      counter--;
    }

    MyString[] result = new MyString[counter];
    int j = 0, k = 1;

    result[0] = this.substring(0,start[0]);
    for (int i = 1; i < result.length-1; i++){
      result[i] = this.substring(end[j++], start[k++]);
    }
    result[result.length-1] = this.substring(end[end.length-1],this.length());
    return result;
  }

  // split with String
  public MyString[] split(String regex) {
    return split(new MyString(regex));
  }

  // trim
  public MyString trim() {
    // WRITE YOUR CODE HERE
    int start = -1, end = -1;

    for (int i = 0; i < this.length(); i++){
      if (this.charAt(i) != ' ' && start == -1){
        start = i;
        break;
      }
    }

    for (int i = this.length()-1 ; i >= 0; i--){
      if (this.charAt(i) != ' ' && end == -1){
        end = i;
        break;
      }
    }

    return this.substring(start,end+1);
  }

  // toLowerCase
  public MyString toLowerCase() {
    // WRITE YOUR CODE HERE
    char[] result = new char[this.length()];
    for (int i = 0; i < this.length(); i++){
      result[i] = Character.toLowerCase(this.charAt(i));
    }
    return new MyString(result);
  }

  // toUpperCase
  public MyString toUpperCase() {
    // WRITE YOUR CODE HERE
    char[] result = new char[this.length()];
    for (int i = 0; i < this.length(); i++){
      result[i] = Character.toUpperCase(this.charAt(i));
    }
    return new MyString(result);
  }

  // equalsIgnoreCase
  public boolean equalsIgnoreCase(MyString s) {
    // WRITE YOUR CODE HERE
    MyString s1 =  this.toLowerCase();
    MyString s2 = s.toLowerCase();
    return s1.equals(s2);
  }

  // compareTo
  public int compareTo(MyString s) {
    // WRITE YOUR CODE HERE
    int val = 0;
    for (int i = 0; i < this.length(); i++){
      if (this.charAt(i) != s.charAt(i)){
        val = this.charAt(i) - s.charAt(i);
        break;
      }
    }
    return val;
  }

  // compareToIgnoreCase
  public int compareToIgnoreCase(MyString s) {
    // WRITE YOUR CODE HERE
    MyString s1 = this.toLowerCase();
    MyString s2 = s.toLowerCase();
    return s1.compareTo(s2);
  }

  // startsWith
  public boolean startsWith(MyString s) {
    // WRITE YOUR CODE HERE
    boolean bool = true;
    if (this.charAt(0) != s.charAt(0)) {
      bool = false;
    }
    else {
      for (int j = 0; j < s.length(); j++) {
        if (this.charAt(j) != s.charAt(j)) {
          bool = false;
          break;
        }
      }
    }
    return bool;
  }

  // endsWith
  public boolean endsWith(MyString s) {
    // WRITE YOUR CODE HERE
    boolean bool = true;
    if (this.charAt(this.length()-1) != s.charAt(s.length()-1)){
      bool = false;
    }
    else{
      int i = this.length()-1;
      for (int j = s.length()-1; j >= 0; j--) {
        if (this.charAt(i--) != s.charAt(j)) {
          bool = false;
          break;
        }
      }
    }
    return bool;
  }


  // contains
  public boolean contains(MyString s) {
    // WRITE YOUR CODE HERE
    boolean bool = true;
    if (this.indexOf(s) == -1 ){
      bool = false;
    }
    return bool;
  }

  // valueOf
  public static MyString valueOf(Number i) {
    // WRITE YOUR CODE HERE
    String s = i.toString();
    return new MyString(s);
  }

  // equals
  public boolean equals(Object o) {
    // WRITE YOUR CODE HERE
    return this.equals(o);
  }

  // equals for String
  public boolean equals(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 =  new MyString(s);
    return this.equals(s1);
  }

  // equals MyString
  public boolean equals(MyString s) {
    // WRITE YOUR CODE HERE
    boolean bool = true;
    for (int i = 0; i < this.length(); i++){
      if (this.charAt(i) != s.charAt(i)){
        bool = false;
      }
    }
    return bool;
  }

  // hashCode
  public int hashCode() {
    int h = 0;
    for (int i = 0; i < length(); i++) {
      h = 31 * h + charAt(i);
    }
    return h;
  }

  //  All wrapper code below this line
  //#region
  // startsWith String
  public boolean startsWith(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.startsWith(s1);
  }

  // endsWith String
  public boolean endsWith(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.endsWith(s1);
  }

  // contains String
  public boolean contains(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.contains(s1);
  }

  // indexOf String
  public int indexOf(String s) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.indexOf(s1);
  }

  // indexOf String from index
  public int indexOf(String s, int fromIndex) {
    // WRITE YOUR CODE HERE
    MyString s1 = new MyString(s);
    return this.indexOf(s1, fromIndex);
  }
  //#endregion

  // indexOf char
  public int indexOf(char ch) {
    // WRITE YOUR CODE HERE
    return this.indexOf(ch,0);
  }

  // indexOf char from index
  public int indexOf(char ch, int fromIndex) {
    // WRITE YOUR CODE HERE
    int index = -1;
    for (int i = fromIndex; i < this.length(); i++){
      if (this.charAt(i) == ch && index == -1){
        index = i;
      }
    }
    return index;
  }
}