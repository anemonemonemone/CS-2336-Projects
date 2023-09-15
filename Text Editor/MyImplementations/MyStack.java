package MyImplementations;
public class MyStack<E> {
  private MyArrayList<E> list = new MyArrayList<E>();
  // default capacity is 16
  private static final int DEFAULT_CAPACITY = 16;

  // Empty constructor
  public MyStack() {
    this(DEFAULT_CAPACITY);
  }
  
    MyStack(MyArrayList<E> list) {
    // Create a new MyGenericStack instance from the given list
    this(list.size());
    for (int i = 0; i < list.size(); i++) {
      this.list.add(list.get(i));
    }
  }

  MyStack(E[] array) {
    // Create a new MyGenericStack instance from the given array
    this(array.length);
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }
  }

  MyStack(MyStack<E> stack) {
    // Create a new ArrayList from the given stack
    this(stack.getSize());
    for (int i = 0; i < stack.getSize(); i++) {
      list.add(stack.get(i));
    }
  }

  MyStack(int capacity) {
    // Create a new ArrayList with the given capacity
    list = new MyArrayList<E>(capacity);
  }

  public E get(int index) {
    // Return the element at the given index
    // WRITE YOUR CODE HERE
    return list.get(index);
  }

  public boolean isEmpty() {
    // Return true if the stack is empty
    // WRITE YOUR CODE HERE
    boolean result = true;
    for (int i = 0; i < list.size(); i++){
      if (list.get(i) != null){
        result = false;
      }
    }
    return result;
  }

  public void push(E e) {
    // Add the given object to the top of the stack
    // WRITE YOUR CODE HERE
    list.add(e);
  }

  public E pop() {
    // Remove and return the top element from the stack
    // WRITE YOUR CODE HERE
    return list.remove(getSize()-1);
  }

  public int getSize() {
    // Return the size of the stack
    // WRITE YOUR CODE HERE
    return list.size();
  }

  public E peek() {
    // Return the top element from the stack
    // WRITE YOUR CODE HERE
    return list.get(getSize()-1);
  }
  
  @Override
  public String toString() {
    return "stack: " + list.toString();
  }
}
