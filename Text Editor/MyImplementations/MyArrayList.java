package MyImplementations;

public class MyArrayList<E> implements MyList<E> {
  public static final int INITIAL_CAPACITY = 16;
  @SuppressWarnings("unchecked") private E[] data = (E[]) new Object[INITIAL_CAPACITY];

  private int size = 0; // Number of elements in the list

  /** Create an empty list */
  public MyArrayList() {
  }

  @SuppressWarnings("unchecked")
  public MyArrayList(int capacity) {
    data = (E[]) new Object[capacity];
  }

  /** Create a list from an array of objects */
  public MyArrayList(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      add(objects[i]); // Warning: don’t use super(objects)!
  }

  @Override /** Add a new element at the specified index */
  public void add(int index, E e) {
    // Ensure the index is in the right range
    // WRITE YOUR CODE HERE
    if (size == data.length){
      E[] temp = (E[]) new Object[data.length];
      this.ensureCapacity();
      E temp1 = data[index];
      E temp2 = data[index+1];
      data[index] = e;
      for (int i = index; i < data.length-1; i++){
        data[i+1] = temp1;
        temp1 = temp2;
        if (i+1 < data.length) {
          temp2 = data[i + 1];
        }
      }
      size++;
    }
    else{
      E temp1 = data[index];
      E temp2 = data[index+1];
      data[index] = e;
      for (int i = index; i < data.length-1; i++){
        data[i+1] = temp1;
        temp1 = temp2;
        if (i+1 < data.length) {
          temp2 = data[i + 1];
        }
      }
      size ++;
    }
  }

  /** Create a new larger array, double the current size + 1 */
  private void ensureCapacity() {
    // WRITE YOUR CODE HERE
    int len = data.length;
    E[] temp = (E[]) new Object[len];
    for (int i = 0; i < len; i++){
      temp[i] = data[i];
    }
    data = (E[]) new Object[len*2+1];
    for (int i = 0; i < len; i++){
      data[i] = temp[i];
    }
  }

  /** Clear the list */
  @SuppressWarnings("unchecked")
  public void clear() {
    // WRITE YOUR CODE HERE
    for (int i = 0; i < data.length; i++){
      data[i] = null;
    }
  }

  /** Return true if this list contains the element */
  public boolean contains(Object e) {
    // WRITE YOUR CODE HERE
    boolean result = false;
    for (int i = 0; i < data.length; i++){
      if (data[i] == e){
        result = true;
      }
    }
    return result;
  }

  @Override /** Return the element at the specified index */
  public E get(int index) {
    // WRITE YOUR CODE HERE
    return data[index];
  }

  // Check whether the index is in the range of 0 to size - 1
  // throw IndexOutOfBoundsException if it is not
  private void checkIndex(int index) {
    // WRITE YOUR CODE HERE
    try{
    }catch (IndexOutOfBoundsException e){
      e.printStackTrace();
    }
  }

  @Override /**
   * Return the index of the first matching element
   * in this list. Return -1 if no match.
   */
  public int indexOf(Object e) {
    // WRITE YOUR CODE HERE
    int result = -1;
    for (int i = 0; i < data.length; i++){
      if (data[i] == e){
        result = i;
      }
    }
    return result;
  }

  @Override /**
   * Return the index of the last matching element
   * in this list. Return -1 if no match.
   */
  public int lastIndexOf(E e) {
    // WRITE YOUR CODE HERE
    int result = -1;
    for (int i = data.length -1; i >= 0; i--){
      if (data[i]==e){
        result = i;
      }
    }
    return result;
  }

  @Override /**
   * Remove the element at the specified position
   * in this list. Shift any subsequent elements to the left.
   * Return the element that was removed from the list.
   */
  public E remove(int index) {
    checkIndex(index);
    // WRITE YOUR CODE HERE
    E[] temp = (E[]) new Object[data.length];
    E result = data[index];
    System.arraycopy(data,0,temp,0,index);
    System.arraycopy(data,index+1, temp,index,this.data.length-index-1);
    System.arraycopy(temp,0,data,0,temp.length);
    size--;
    return result;
//    E result = data[index];
//    for (int i = index; i < data.length-1; i++){
//      data[i] = data[i+1];
//    }
//    return result;
  }

  @Override /**
   * Replace the element at the specified position
   * in this list with the specified element.
   */
  public E set(int index, E e) {
    checkIndex(index);
    E old = data[index];
    data[index] = e;
    return old;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("[");

    for (int i = 0; i < size; i++) {
      result.append(data[i]);
      if (i < size - 1)
        result.append(", ");
    }

    return result.toString() + "]";
  }

  /** Trims the capacity to current size */
  public void trimToSize() {
    if (size != data.length) {
      @SuppressWarnings("unchecked") E[] newData = (E[]) (new Object[size]);
      System.arraycopy(data, 0, newData, 0, size);
      data = newData;
    } // If size == capacity, no need to trim
  }

  // @Override /** Override iterator() defined in Iterable */
  // public java.util.Iterator<E> iterator() {
  //   return new ArrayListIterator();
  // }

  private class ArrayListIterator
          implements java.util.Iterator<E> {
    private int current = 0; // Current index

    @Override
    public boolean hasNext() {
      return current < size;
    }

    @Override
    public E next() {
      return data[current++];
    }

    @Override // Remove the element returned by the last next()
    public void remove() {
      if (current == 0) // next() has not been called yet
        throw new IllegalStateException();
      MyArrayList.this.remove(--current);
    }
  }

  /** Return the number of elements in this list */
  public int size() {
    return size;
  }
}