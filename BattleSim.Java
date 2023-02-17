import java.util.*;

public class BattleSim{
  
  private ArrayList<Pokemon> team1;
  private ArrayList<Pokemon> team2;
  private Pokemon active1;
  private Pokemon active2;
  private int hp1;
  private int hp2;
  
  private Scanner s = new Scanner(System.in);
  
  public BattleSim(ArrayList<Pokemon> t1, ArrayList<Pokemon> t2){
    team1 = t1;
    team2 = t2;
  }
  public void takeTurn(){
  }
  public void chooseMoves(){
    System.out.println("\nPlayer 1: Select the move you would like "+active1+" to use.");
    System.out.println(active1.getMoves(1)+"\n");
    String str = s.nextLine();
    System.out.println(hp2+", "+active1.getDamage(str, active1, active2));
    hp2 -= active1.getDamage(str, active1, active2);
    System.out.println("\nPlayer 2: Select the move you would like "+active2+" to use.");
    System.out.println(active2.getMoves(1)+"\n");
    str = s.nextLine();
    System.out.println(hp1+", "+active2.getDamage(str, active2, active1));
    hp1 -= active2.getDamage(str, active2, active1);
  }
  public void choosePokemon(){
    System.out.println("\nPlayer 1: Select the pokemon you would like to bring in. "+team1);
    String str = s.nextLine();
    for(int i = 0; i < team1.size(); i++){
      if(team1.get(i).toString().equalsIgnoreCase(str)){
        active1 = team1.get(i);
      }
    }
    hp1 = active1.getStats()[0]*2;
    System.out.println("\nPlayer 2: Select the pokemon you would like to bring in. "+team2);
    str = s.nextLine();
    for(int i = 0; i < team2.size(); i++){
      if(team2.get(i).toString().equalsIgnoreCase(str)){
        active2 = team2.get(i);
      }
    }
    hp2 = active2.getStats()[0]*2;
  }
}
