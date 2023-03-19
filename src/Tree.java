/**
 * represents a Tree
 */

public class Tree {

   /**
    * @param age age of a tree
    */
    private int age;
  
    /**
     * constructor, creates a tree
     */
    public Tree(){
      age = 0;
    }
  
    /**
     * constructor, creates a tree with a given age
     */
    public Tree(int age) {
      this.age = age;
    }

    /**
     * copy constructor of an existing tree
     * @param t existing tree
     */
    public Tree(Tree t){
       age = t.age;
    }
  
    /**
     * returns how old is a tree
     * @return age of a tree
     */
    public int getAge() {
      return this.age;
    }
  
    /**
     * sets a new tree age
     * @param age new age of a tree
     */
    public void setAge(int age) {
      this.age = age;
    }

  }
  