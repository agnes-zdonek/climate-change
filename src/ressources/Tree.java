
public class Tree {

    private int age;
  
    //constructeur qui crée un arbre
    public Tree(){
      age = 0;
    }
  
    //constructeur qui crée un arbre
    public Tree(int age) {
      this.age = age;
    }

    //constructeur qui crée un arbre
    public Tree(Tree t){
       age = t.age;
    }
  
    //accesseur pour l'âge du arbre
    public int getAge() {
      return this.age;
    }
  
    //mutateur pour l'âge du arbre
    public void setAge(int age) {
      this.age = age;
    }

  }
  