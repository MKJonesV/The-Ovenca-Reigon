import java.util.*;

public class BattleSim{
  
  private ArrayList<Pokemon> team1;
  private ArrayList<Pokemon> team2;
  private Pokemon active1;
  private Pokemon active2;
  private int damage1;
  private int damage2;
  private int[] stats1;
  private int[] stats2;
  private String move1;
  private String move2;
  private boolean fainted1;
  private boolean fainted2;
  
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
    move1 = s.nextLine();
    damage1 = active1.getDamage(move1, active1, active2);
    System.out.println("\nPlayer 2: Select the move you would like "+active2+" to use.");
    System.out.println(active2.getMoves(1)+"\n");
    move2 = s.nextLine();
    damage2 = active2.getDamage(move2, active2, active1);
  }
  public void executeMoves(){
    if(stats1[4] > stats2[4]){
      stats2[0] -= damage1;
      if(stats2[0] < 1){
        fainted2 = true;
      }else{
        stats1[0] -= damage2;
        if(stats1[0] < 1){
          fainted1 = true;
        }
      }
    }
  }
  public void chooseFirstPokemon(){
    System.out.println("\nPlayer 1: Select the pokemon you would like to bring in. "+team1);
    String str = s.nextLine();
    for(int i = 0; i < team1.size(); i++){
      if(team1.get(i).toString().equalsIgnoreCase(str)){
        active1 = team1.get(i);
      }
    }
    stats1 = active1.getStats();
    stats1[0] *= 2;
    // Sets Player 1's first pokemon
    System.out.println("\nPlayer 2: Select the pokemon you would like to bring in. "+team2);
    str = s.nextLine();
    for(int i = 0; i < team2.size(); i++){
      if(team2.get(i).toString().equalsIgnoreCase(str)){
        active2 = team2.get(i);
      }
    }
    stats2 = active2.getStats();
    stats2[0] *= 2;
    // Sets Player 2's first pokemon
  }
  public void nextPokemon(){
    if(fainted1){
      System.out.println("\nPlayer 1: Select the pokemon you would like to bring in. "+team1);
      String str = s.nextLine();
      for(int i = 0; i < team1.size(); i++){
        if(team1.get(i).toString().equalsIgnoreCase(str)){
          active1 = team1.get(i);
        }
      }
      stats1 = active1.getStats();
      stats1[0] *= 2;
    }
    if(fainted2){
      System.out.println("\nPlayer 2: Select the pokemon you would like to bring in. "+team2);
      String str = s.nextLine();
      for(int i = 0; i < team2.size(); i++){
        if(team2.get(i).toString().equalsIgnoreCase(str)){
          active2 = team2.get(i);
        }
      }
      stats2 = active2.getStats();
      stats2[0] *= 2;
    }
  }
  public void battle(){
    {
      //startTurnFunctions();
      chooseMoves();
      executeMoves();
      //endTurnFunctions();
      if(fainted1 || fainted2){
        nextPokemon();
      }
      if(team1.size() > 0 && team2.size() > 0){
        battle();
      }else{
        //victory();
      }
    }
  }
}
