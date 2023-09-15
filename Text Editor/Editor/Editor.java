package Editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import MyImplementations.MyArrayList;
import MyImplementations.MyStack;
import MyImplementations.MyString;

public class Editor {
  /** cursor row position */
  int row;
  /** cursor column position */
  int col;
  /** the text of the editor */
  MyArrayList<MyString> text;
  /** the undo stack */
  MyStack<EditorAction> undoStack;
  /** the redo stack */
  MyStack<EditorAction> redoStack;

  // Current file name
  MyString fileName;

  class EditorAction {
    /** the row of the action */
    int row;
    /** the column of the action */
    int col;
    /** the text of the action */
    MyString text;
    /** the length of the action */
    int length;
    /** the action type */
    ActionType type;
  }

  enum ActionType {
    /** insert action */
    INSERT,
    /** delete action */
    DELETE,
    /** replace action */
    REPLACE
  }



  /**
   * Constructor
   */
  public Editor(String myFilename) {
    this(new MyString(myFilename));
  }

  public Editor(MyString fileName) {
    this.fileName = fileName;
    undoStack = new MyStack<EditorAction>();
    redoStack = new MyStack<EditorAction>();
    open(fileName);
  }

  /**
   * Open the file with the given name and read the contents into the editor.
   */
  void open(String fileName) {
    open(new MyString(fileName));
  }

  void open(MyString fileName) {
    // read the file into the text
    // set the cursor to the beginning of the file
    // set the file name
    // WRITE YOUR CODE HERE
    try {
      BufferedReader reader = Files.newBufferedReader(Paths.get(fileName.toString()));
      text = new MyArrayList<>();
      String line = reader.readLine();
      while (line != null){
        text.add(new MyString(line));
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    this.col= 0;
    this.row = 0;
  }
  /**
   * Save the current text to currently open file.
   */
  void save() {
    saveAs(fileName);
  }

  /**
   * Save the current text to the given file.
   */
  void saveAs(String fileName) {
    saveAs(new MyString(fileName));
  }

  void saveAs(MyString fileName) {
    // WRITE YOUR CODE HERE
    try {
      BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName.toString()));
      for (int i = 0; i < text.size(); i++) {
        writer.write(text.get(i).toCharArray());
        writer.newLine();
      }
      writer.close();
      this.fileName = fileName;
    }catch (IOException e) {
      e.printStackTrace();
      return;
    }
  }

  /**
   * Undo the last action.
   */
  void undo() {
    // WRITE YOUR CODE HERE
    EditorAction action = undoStack.pop();
    switch (action.type){
      case DELETE:
        redoStack.push(action);
        moveCursor(action.row, action.col);
        insert(action.text, false);
        break;
      case INSERT:
        redoStack.push(action);
        moveCursor(action.row, action.col);
        delete(action.text.length(),false);
        break;
      case REPLACE:
        moveCursor(action.row, action.col);
        replace(action.length, action.text, true);
        redoStack.push(undoStack.pop());
        break;
    }
  }

  /**
   * Redo the last undone action.
   */
  void redo() {
    // WRITE YOUR CODE HERE
    EditorAction action = redoStack.pop();
    switch (action.type){
      case DELETE:
        undoStack.push(action);
        moveCursor(action.row, action.col);
        delete(action.text.length(), false);
        break;
      case INSERT:
        undoStack.push(action);
        moveCursor(action.row, action.col);
        insert(action.text, false);
        break;
      case REPLACE:
        moveCursor(action.row, action.col);
        replace(action.length, action.text, true);
        break;
    }
  }

  /**
   * Save the state of the EditorAction and push it onto the undo stack. used by insert and delete
   * @param type
   * @param s
   */
  void saveState(ActionType type, MyString s) {
    // WRITE YOUR CODE HERE
    EditorAction action = new EditorAction();
    action.row = this.row;
    action.col = this.col;
    action.text = s;
    action.type = type;
    undoStack.push(action);
  }

  /**
   * Save the state of the EditorAction and push it onto the undo stack. used by replace
   * @param type
   * @param s
   * @param length
   */
  void saveState(ActionType type, MyString s, int length) {
    // WRITE YOUR CODE HERE
    EditorAction action = new EditorAction();
    action.row = this.row;
    action.col = this.col;
    action.text = s;
    action.length = length;
    action.type = type;
    undoStack.push(action);
  }

  /**
   * Insert the given string at the current cursor position.
   * The cursor position is updated to point to the end of the inserted string.
   */
  void insert(String s) {
    insert(new MyString(s));
  }

  void insert(MyString s) {
    insert(s, true);
  }

  void insert(MyString s, boolean saveState) {
    // WRITE YOUR CODE HERE
    if (saveState == true){
      saveState(ActionType.INSERT, s);
    }
    MyString line = text.get(row);
    MyString leftSubstring = line.substring(0,col);
    MyString rightSubstring = line.substring(col,line.length());
    MyString result = leftSubstring.concat(s).concat(rightSubstring);
    text.set(row,result);
    col += s.length();
  }

  void delete(int n) {
    delete(n, true);
  }

  /**
   * Delete n characters at the current cursor position.
   * The cursor position is updated to point to the end of the deleted string.
   */
  void delete(int n, boolean saveState) {
    // WRITE YOUR CODE HERE
    MyString line = text.get(row);
    if (saveState ==  true){
      MyString deletedSubstring = line.substring(col,col+n);
      saveState(ActionType.DELETE,deletedSubstring);
    }
    MyString leftSubstring = line.substring(0,col);
   if (col+n > line.length()){
     MyString rightSubstring = line.substring(line.length(),line.length());
     MyString result = leftSubstring.concat(rightSubstring);
     text.set(row,result);
   }
   else{
     MyString rightSubstring = line.substring(col+n,line.length());
     MyString result = leftSubstring.concat(rightSubstring);
     text.set(row,result);
   }
  }

  /**
   * Replace the character at the current cursor position with the given
   * character.
   * The cursor position is updated to point to the end of the deleted string.
   */
  void replace(int n, String s) {
    replace(n, new MyString(s));
  }

  void replace(int n, MyString s) {
    replace(n, s, true);
  }

  /**
   * Replace the n characters at the current cursor position with the given string.
   * The cursor position is updated to point to the end of the replaced string.
   */
  void replace(int n, MyString s, boolean saveState) {
    // WRITE YOUR CODE HERE
    MyString line = text.get(row);
    if (saveState == true){
      MyString substring;
      if (col+n > line.length()){
        substring = line.substring(col,line.length());
      }
      else{
        substring = line.substring(col, col+n);
      }
      saveState(ActionType.REPLACE, substring, n);
    }
    delete(n,false);
    insert(s,false);
  }

  /**
   * Find the first instance of given string in the editor and set the cursor to
   * that position.
   */
  int[] find(String s) {
    return find(new MyString(s));
  }

  int[] find(MyString s) {
    // WRITE YOUR CODE HERE
    for (int i = 0; i < text.size(); i++){
      if (text.get(i).indexOf(s)!= -1){
        row = i;
        col = text.get(i).indexOf(s);
        break;
      }
    }
    return null;
  }

  /**
   * Move the cursor to the given position.
   */
  void moveCursor(int row, int col) {
    // WRITE YOUR CODE HERE
    this.row = row;
    this.col = col;
  }

  /**
   * Return the current cursor position.
   */
  int[] getCursor() {
    return new int[] {row, col};
  }

  /**
   * Move the cursor to the given position.
   */
  void moveCursor(int[] rowcol) {
    // WRITE YOUR CODE HERE
    row = rowcol[0];
    col = rowcol[1];
  }

  /** return the entire line in row */
  MyString getText(int row) {
    return text.get(row);
  }

  /** return the line in row from col to the end */
  MyString getText(int row, int col) {
// WRITE YOUR CODE HERE
    return text.get(row).substring(col,text.get(row).length());
  }

  /** return the line from col to n character length */
  MyString getText(int row, int col, int n) {
    // WRITE YOUR CODE HERE
    return text.get(row).substring(col,Math.min(col+n,text.get(row).length()));
  }
}