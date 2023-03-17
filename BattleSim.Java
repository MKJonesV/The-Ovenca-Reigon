import java.util.*;

public class BattleSim{
  
  private ArrayList<Pokemon> team1;
  private ArrayList<Pokemon> team2;
  private ArrayList<String> status1 = new ArrayList<String>();
  private ArrayList<String> status2 = new ArrayList<String>();
  private Pokemon active1;
  private Pokemon active2;
  private int damage1;
  private int damage2;
  private int maxHp1;
  private int maxHp2;
  private int count1;
  private int count2;
  private int[] stats1;
  private int[] stats2;
  private String move1;
  private String move2;
  private String effectivity1;
  private String effectivity2;
  private String current1 = "";
  private String current2 = "";
  private boolean fainted1;
  private boolean fainted2;
  
  private Scanner s = new Scanner(System.in);
  
  public BattleSim(ArrayList<Pokemon> t1, ArrayList<Pokemon> t2){
    team1 = t1;
    team2 = t2;
    status1.add(0, "");
    status2.add(0, "");
  }
  public void chooseMoves(){
    System.out.println("\n"+active1.getMoves(1)+"\n");
    System.out.println("Player 1: Select the move you would like "+active1+" to use. (Enter the move exactly as shown, excluding any '*' charachters)");
    move1 = s.nextLine();
    damage1 = active1.getDamage(move1, active1, active2);
    effectivity1 = active1.effectivity(move1, active2.getType());
    System.out.println("\n"+active2.getMoves(1)+"\n");
    System.out.println("Player 2: Select the move you would like "+active2+" to use. (Enter the move exactly as shown, excluding any '*' charachters)");
    move2 = s.nextLine();
    damage2 = active2.getDamage(move2, active2, active1);
    effectivity2 = active2.effectivity(move2, active1.getType());
  }
  public void executeMoves(){
    if(damage1 == 0 || damage2 == 0){
      if(stats1[5] > stats2[5]){
        p1StatusMoves();
        p2StatusMoves();
      }else{
        p2StatusMoves();
        p1StatusMoves();
      }
    }
    if(stats1[5] > stats2[5]){
      System.out.println(active1 + " used " + move1 + ". "+effectivity1);
      stats2[0] -= damage1;
      p1ExtraMoveEffects();
      if(stats2[0] < 1){
        fainted2 = true;
        printBattle();
      }else{
        System.out.println(active2 + " used " + move2 + ". "+effectivity2);
        stats1[0] -= damage2;
        p2ExtraMoveEffects();
        printBattle();
        if(stats1[0] < 1){
          fainted1 = true;
        }
      }
    } else if(stats1[5] < stats2[5]){
      System.out.println(active2 + " used " + move2 + ". "+effectivity2);
      stats1[0] -= damage2;
      p2ExtraMoveEffects();
      if(stats1[0] < 1){
        fainted1 = true;
        printBattle();
      }else{
        System.out.println(active1 + " used " + move1 + ". "+effectivity1);
        stats2[0] -= damage1;
        p1ExtraMoveEffects();
        printBattle();
        if(stats2[0] < 1){
          fainted2 = true;
        }
      }
    } else {
      System.out.println(active1 + " used " + move1 + ". "+effectivity1);
      System.out.println(active2 + " used " + move2 + ". "+effectivity2);
      stats2[0] -= damage1;
      stats1[0] -= damage2;
      p1ExtraMoveEffects();
      p2ExtraMoveEffects();
      printBattle();
      if(stats1[0] < 1){
        fainted1 = true;
      }
      if(stats2[0] < 1){
        fainted2 = true;
      }
    }
    damage1 = 0;
    damage2 = 0;
  }
  
  public void p1ExtraMoveEffects(){
    String[] a1 = active1.getMoveList();
    for(int i = 0; i < a1.length; i++){
      if(a1[i].indexOf(move1) != -1){
        if(a1[i].substring(a1[i].length()-3).equals("***")){
          
        }
        if(a1[i].substring(a1[i].length()-2).equals("**") && !a1[i].substring(a1[i].length()-3).equals("***")){
          stats1[0] += damage1/2;
          if(stats1[0] > maxHp1){
            stats1[0] = maxHp1;
          }
        }
        if(a1[i].substring(a1[i].length()-1).equals("*") && !a1[i].substring(a1[i].length()-2).equals("**")){
          stats1[0] -= damage1/4;
          if(stats1[0] < 1){
            fainted1 = true;
          }
        }
      }
    }
  }
  public void p2ExtraMoveEffects(){
    String[] a2 = active2.getMoveList();
    for(int i = 0; i < a2.length; i++){
      if(a2[i].indexOf(move2) != -1){
        if(a2[i].substring(a2[i].length()-3).equals("***")){
          
        }else if(a2[i].substring(a2[i].length()-2).equals("**") && !a2[i].substring(a2[i].length()-3).equals("***")){
          stats2[0] += damage2/2;
          if(stats2[0] > maxHp2){
            stats2[0] = maxHp2;
          }
        }else if(a2[i].substring(a2[i].length()-1).equals("*") && !a2[i].substring(a2[i].length()-2).equals("**")){
          stats2[0] -= damage2/4;
          if(stats2[0] < 1){
            fainted2 = true;
          }
        }
      }
    }
  }
  public void p1StatusMoves(){
    if(move1.equalsIgnoreCase("Metronome")){
      move1 = active1.randomMove();
      damage1 = active1.getDamage(move1, active1, active2);
    }
    if((move1.equalsIgnoreCase("Protect") || move1.equalsIgnoreCase("Detect")) && count1 == 0){ 
      damage2 = 0;
      count1 = 2;
    }
    // Protection Status Moves
    if(move1.equalsIgnoreCase("Synthesis") || move1.equalsIgnoreCase("Shore Up") || move1.equalsIgnoreCase("Roost") || move1.equalsIgnoreCase("Moonlight")){
      stats1[0] += active1.getStats()[0]/2;
      if(stats1[0] > maxHp1){
        stats1[0] = maxHp1;
      }
    }
    // Healing Moves
    if(move1.equalsIgnoreCase("Automize")){
      stats1[5] *= 2;
    }
    if(move1.equalsIgnoreCase("Nasty Plot")){
      stats1[1] *= 2;
    }
    if(move1.equalsIgnoreCase("Tail Glow")){
      stats1[3] *= 2;
    }
    if(move1.equalsIgnoreCase("Bulk Up")){
      stats1[1] = (int)(stats1[1]*1.5);
      stats1[2] = (int)(stats1[2]*1.5);
    }
    if(move1.equalsIgnoreCase("Calm Mind")){
      stats1[3] = (int)(stats1[3]*1.5);
      stats1[4] = (int)(stats1[5]*1.5);
    }
    if(move1.equalsIgnoreCase("Dragon Dance")){
      stats1[1] = (int)(stats1[1]*1.5);
      stats1[5] = (int)(stats1[5]*1.5);
    }
    // Stat Enhancing Moves
    if(move1.equalsIgnoreCase("Will-o-wisp") && (!active2.getType().getPrimaryType().equals("Fire") || !active2.getType().getSecondaryType().equals("Fire"))){
      status2.set(0, "(BRN)");
      stats2[1] /= 2;
    }
    if(move1.equalsIgnoreCase("Thunder Wave") && (!active2.getType().getPrimaryType().equals("Electric") || !active2.getType().getSecondaryType().equals("Electric"))){
      status2.set(0, "(PRZ)");
      stats2[5] /= 2;
    }
    if(move1.equalsIgnoreCase("Toxic") && (!active2.getType().getPrimaryType().equals("Steel") || !active2.getType().getSecondaryType().equals("Steel"))){
      status2.set(0, "(PSN)");
    }
    if(move1.equalsIgnoreCase("Confuse Ray") && (!active2.getType().getPrimaryType().equals("Psychic") || !active2.getType().getSecondaryType().equals("Psychic"))){
      status2.set(0, "(CNF)");
      stats2[3] /= 2;
    }
    // Affliction Moves
    if(move1.equalsIgnoreCase("Aqua Ring") || move1.equalsIgnoreCase("Stealth Rock") || move1.equalsIgnoreCase("Reflect") || move1.equalsIgnoreCase("Light Screen") || move1.equalsIgnoreCase("Aurora Veil")){
      status1.add(move1);
      count1 = 5;
    }
    // Status Condition Moves
  }
  public void p2StatusMoves(){
    if(move2.equalsIgnoreCase("Metronome")){
      move2 = active2.randomMove();
      move2 = move2.substring(0, move2.indexOf(" "));
      damage2 = active2.getDamage(move2, active2, active1);
    }
    if((move2.equalsIgnoreCase("Protect") || move2.equalsIgnoreCase("Detect")) && count2 == 0){ 
      damage1 = 0;
      count2 = 2;
    }
    // Protection Status Moves
    if(move2.equalsIgnoreCase("Synthesis") || move2.equalsIgnoreCase("Shore Up") || move2.equalsIgnoreCase("Roost") || move2.equalsIgnoreCase("Moonlight")){
      stats2[0] += active2.getStats()[0]/2;
      if(stats2[0] > maxHp2){
        stats2[0] = maxHp2;
      }
    }
    // Healing Moves
    if(move2.equalsIgnoreCase("Automize")){
      stats2[5] *= 2;
    }
    if(move2.equalsIgnoreCase("Nasty Plot")){
      stats2[1] *= 2;
    }
    if(move2.equalsIgnoreCase("Tail Glow")){
      stats2[3] *= 2;
    }
    if(move2.equalsIgnoreCase("Bulk Up")){
      stats2[1] = (int)(stats2[1]*1.5);
      stats2[2] = (int)(stats2[2]*1.5);
    }
    if(move2.equalsIgnoreCase("Calm Mind")){
      stats2[3] = (int)(stats2[3]*1.5);
      stats2[4] = (int)(stats2[5]*1.5);
    }
    if(move2.equalsIgnoreCase("Dragon Dance")){
      stats2[1] = (int)(stats2[1]*1.5);
      stats2[5] = (int)(stats2[5]*1.5);
    }
    // Stat Enhancing Moves
    if(move2.equalsIgnoreCase("Will-o-wisp") && (!active1.getType().getPrimaryType().equals("Fire") || !active1.getType().getSecondaryType().equals("Fire"))){
      status1.set(0, "(BRN)");
      stats1[1] /= 2;
    }
    if(move2.equalsIgnoreCase("Thunder Wave") && (!active1.getType().getPrimaryType().equals("Electric") || !active1.getType().getSecondaryType().equals("Electric"))){
      status1.set(0, "(PRZ)");
      stats1[5] /= 2;
    }
    if(move2.equalsIgnoreCase("Toxic") && (!active1.getType().getPrimaryType().equals("Steel") || !active1.getType().getSecondaryType().equals("Steel"))){
      status1.set(0, "(PSN)");
    }
    if(move2.equalsIgnoreCase("Confuse Ray") && (!active1.getType().getPrimaryType().equals("Psychic") || !active1.getType().getSecondaryType().equals("Psychic"))){
      status1.set(0, "(CNF)");
      stats1[3] /= 2;
    }
    // Affliction Moves
    if(move2.equalsIgnoreCase("Aqua Ring") || move2.equalsIgnoreCase("Stealth Rock") || move2.equalsIgnoreCase("Reflect") || move2.equalsIgnoreCase("Light Screen") || move2.equalsIgnoreCase("Aurora Veil")){
      status2.add(move2);
      count2 = 5;
    }
    // Status Condition Moves
  }
  
  public void chooseFirstPokemon(){
    System.out.println("Player 1: Select the pokemon you would like to bring in. "+team1);
    String str = s.nextLine();
    for(int i = 0; i < team1.size(); i++){
      if(team1.get(i).toString().equalsIgnoreCase(str)){
        active1 = team1.get(i);
      }
    }
    stats1 = active1.getStats();
    stats1[0] *= 2;
    maxHp1 = stats1[0];
    // Sets Player 1's first pokemon
    System.out.println("Player 2: Select the pokemon you would like to bring in. "+team2);
    str = s.nextLine();
    for(int i = 0; i < team2.size(); i++){
      if(team2.get(i).toString().equalsIgnoreCase(str)){
        active2 = team2.get(i);
      }
    }
    stats2 = active2.getStats();
    stats2[0] *= 2;
    maxHp2 = stats1[0];
    // Sets Player 2's first pokemon
  }
  public void nextPokemon(){
    if(fainted1){
      team1.remove(team1.indexOf(active1));
      fainted1 = false;
      if(team1.size() > 0){
        System.out.println("\nPlayer 1: Select the pokemon you would like to bring in. "+team1);
        String str = s.nextLine();
        for(int i = 0; i < team1.size(); i++){
          if(team1.get(i).toString().equalsIgnoreCase(str)){
            active1 = team1.get(i);
          }
        }
        stats1 = active1.getStats();
        stats1[0] *= 2;
        maxHp1 = stats1[0];
        status1.set(0, "");    
      }
    }
    if(fainted2){
      team2.remove(team2.indexOf(active2));
      fainted2 = false;
      if(team2.size() > 0){
        System.out.println("\nPlayer 2: Select the pokemon you would like to bring in. "+team2);
        String str = s.nextLine();
        for(int i = 0; i < team2.size(); i++){
          if(team2.get(i).toString().equalsIgnoreCase(str)){
            active2 = team2.get(i);
          }
        }
        stats2 = active2.getStats();
        stats2[0] *= 2;
        maxHp2 = stats1[0];
        status2.set(0, "");
      }
    }
  }
  public void startTurnFunctions(){
    printBattle();
    if(count1 != 0){
      count1--;
    }else{
      for(int i = status1.size(); i > 1; i--){
        status1.remove(i);
      }
    }
    if(count2 != 0){
      count2--;
    }else{
      for(int i = status2.size(); i > 1; i--){
        status2.remove(i);
      }
    }
    if(status1.indexOf("Auroura Veil") != -1 && !current1.equals(active1.toString())){
      stats1[4] *= 2;
      stats1[2] *= 2;
      current1 = active1.toString();
    }
    if(status2.indexOf("Auroura Veil") != -1 && !current2.equals(active2.toString())){
      stats2[4] *= 2;
      stats2[2] *= 2;
      current2 = active2.toString();
    }
    if(status1.indexOf("Reflect") != -1 && !current1.equals(active1.toString())){
      stats1[2] *= 2;
      current1 = active1.toString();
    }
    if(status2.indexOf("Reflect") != -1 && !current2.equals(active2.toString())){
      stats2[2] *= 2;
      current2 = active2.toString();
    }
    if(status1.indexOf("Light Screen") != -1 && !current1.equals(active1.toString())){
      stats1[4] *= 2;
      current1 = active1.toString();
    }
    if(status2.indexOf("Light Screen") != -1 && !current2.equals(active2.toString())){
      stats2[4] *= 2;
      current2 = active2.toString();
    }
    // Screen Moves that reduce Damage done to this pokemon. Cannot activate repeatedly
    if(status1.indexOf("(CNF)") == 0){
      stats1[0] -= maxHp1/16;
    }
    if(status2.indexOf("(CNF)") == 0){
      stats2[0] -= maxHp2/16;
    }
  }
  
  public void endTurnFunctions(int x){
    if(status1.indexOf("Stealth Rocks") != -1){
      stats2[0] -= (maxHp2/8);
    }
    if(status2.indexOf("Stealth Rocks") != -1){
      stats1[0] -= (maxHp1/8);
    }
    if(status1.indexOf("Aqua Ring") != -1){
      stats1[0] += (maxHp1/8);
      if(stats1[0] > maxHp1){
          stats1[0] = maxHp1;
      }
    }
    if(status2.indexOf("Aqua Ring") != -1){
      stats2[0] += (maxHp2/8);
      if(stats2[0] > maxHp2){
          stats2[0] = maxHp2;
      }
    }
    if(status1.indexOf("(PSN)") == 0){
      stats1[0] -= maxHp1/8;
    }
    if(status2.indexOf("(PSN)") == 0){
      stats2[0] -= maxHp2/8;
    }
    if(status1.indexOf("(BRN)") == 0){
      stats1[0] -= maxHp1/16; 
    }
    if(status2.indexOf("(BRN)") == 0){
      stats2[0] -= maxHp2/16;
    }
    if(stats1[0] < 1){
      fainted1 = true;
    }
    if(stats2[0] < 1){
      fainted2 = true;
    }
    System.out.println("\nEnd of Turn #"+x);
  }

  public void printBattle(){
    System.out.println("\n P1's Team: "+team1 + "\t\t\t\tP2's Team: "+team2);
    System.out.println((status1.get(0) + " "+ active1 + " (" + active1.getType() + ")" + ": "+stats1[0] + " HP \t\t\t\t\t\t" + status2.get(0) + " " + active2 + " (" + active2.getType() + ")" + ": " + stats2[0] + " HP"));
  }
  public void battle(int x){
    {
      startTurnFunctions();
      chooseMoves();
      executeMoves();
      endTurnFunctions(x);
      x++;
      if((fainted1 || fainted2) && (team1.size() > 0 && team2.size() > 0)){
        nextPokemon();
      }
      if(team1.size() > 0 && team2.size() > 0){
        battle(x);
      }else{
        //victory();
      }
    }
  }
}
