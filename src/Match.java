import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

class Match{
	private int [][]map=new int[32][32];
	private int []cnt=new int[30];
	private int nrow,ncol,nkind;
	private int nres,nhint;
	private Spot marked = null;
	private int dx[] = {1, 0, -1, 0}, dy[] = {0, 1, 0, -1};	
	private boolean visited[][] = new boolean[32][32];
	private Spot corners[] = new Spot[4];
	private SolutionList Hints = new SolutionList(30);
	public void Print(){//Just for Debugging
		System.out.println("nrow="+nrow+" ncol="+ncol+" nkind="+nkind+" nrestart="+nres+" nhint="+nhint);
		System.out.println("The Count Array:");
		for (int i=1;i<=nkind;i++) System.out.print(cnt[i]+'\t');
		System.out.println("\nThe Map Matrix:");
		for (int i=1;i<=nrow;i++){
			for (int j=1;j<=ncol;j++) System.out.print(map[i][j]+"\t");
			System.out.println("");
		}
	}

	public Match(int len){
		nrow=ncol=len;
		nkind = 24;
		nres=2;nhint=5;
		int i;
		for (i=1;i<=nkind;i++) cnt[i]=nrow*ncol/nkind;
		for(i = 1; i <= nrow; i++)
			for(int j = 1; j<= ncol; j++)
				map[i][j] = 1;
		Reshuffle();
		
	}
	
	public void Reshuffle(){
		int []tmp=new int[30];
		int i,j,x;
		for (i=1;i<=nkind;i++) tmp[i]=cnt[i];
		for (i=1;i<=nrow;i++)
			for (j=1;j<=ncol;j++)
				if(map[i][j]>0){
					do{
						x=(int)(nkind*Math.random())+1;
						//x = (int)(nkind*ThreadLocalRandom.current().nextDouble())+1;
					}while (tmp[x]==0);
					tmp[x]--;
					map[i][j]=x;
				}
		Print();
	}
	/*
	public void AdjacentSolution(){// find the adjacent same ones
		for(int i = 1;i <= nrow; i++)
			for(int j = 1; j < ncol; j++){
				if(map[i][j] == map[i][j+1] && map[i][j] != 0) 
					Hints.add(new Solution(map[i][j], new Spot(i,j), new Spot(i, j+1)));
			}
		for(int i = 1; i < nrow; i++)
			for(int j = 1; j<= ncol; j++){
				if(map[i][j] == map[i+1][j] && map[i][j] != 0 ) 
					Hints.add(new Solution(map[i][j], new Spot(i,j), new Spot(i+1, j)));
			}
	}*/
	
	public int getMap(int i, int j){
		return map[i][j];
	}
	
	public boolean inMap( Spot s ){
		if(s.x>=0 && s.y>=0 && s.x<=nrow+1 && s.y<=ncol + 1) return true;
		return false;
	}
	/*
	public void DfsSolution(Spot now, int corner, int direction, Spot begin){
		if(corner > 2) return;
		if(now.k == begin.k){
			int kind = map[now.x][now.y];
			Solution tmp = new Solution(kind, begin, now);
			if (corner >= 1) tmp.corner1 = corners[0];
			if (corner == 2) tmp.corner2 = corners[1];
			Hints.add(tmp);
			return;
		}
		else if(now.k != 0) return;
		for(int i = 0; i < 4; i++){
			Spot tmp = new Spot(now.x + dx[i], now.y + dy[i]);
			tmp.k = map[tmp.x][tmp.y];
			if (!inMap(tmp)) continue;
			if (tmp.k != 0 && tmp.k != begin.k || visited[tmp.x][tmp.y]) continue;
			if (direction!= 5 && direction!= i) { // same direction or not
				corners[corner] = tmp;
				corner++;
			}
			visited[tmp.x][tmp.y] = true;
			DfsSolution(tmp, corner, i, begin);
			visited[tmp.x][tmp.y] = false;
			if (direction!= 5 && direction!= i) {
				corner --;
				corners[corner] = null;
			}
		}
	}
	*/
	public boolean dfs(Spot now, int corner, int direction, Spot end){
		if(corner > 2) return false;
		if(now.x == end.x && now.y == end.y){			
			return true;
		}		
		if(visited[now.x][now.y] == false) visited[now.x][now.y] = true;
		else if(now.k!=0) return false;
		boolean find = false;
		for(int i = 0; i < 4; i++){
			Spot tmp = new Spot(now.x + dx[i], now.y + dy[i]);
			if (!inMap(tmp)) continue;
			tmp.k = map[tmp.x][tmp.y];
			if (tmp.k != 0 && tmp.k != end.k || visited[tmp.x][tmp.y]) continue;
			if (direction!= 5 && direction!= i) { // same direction or not
				if(corner>1) continue;
				corners[corner] = now;
				corner++;
			}
			visited[tmp.x][tmp.y] = true;
			find = dfs(tmp, corner, i, end);
			if ( find ) break;
			visited[tmp.x][tmp.y] = false;
			if (direction!= 5 && direction!= i) {
				corner --;
				corners[corner] = null;
			}
		}
		return find;
	}
	/*
	public Solution bfs(Spot start, Spot end){
		Solution ans = new Solution(map[end.x][end.y], start, end);
		Queue<Node> Q = new LinkedList<Node>();
		visited[start.x][start.y] = true;
		for(int i = 0;i < 4; i++){
			Spot nb = new Spot(start.x+dx[i], start.y+dy[i]);
			if(!inMap(nb)) continue;
			visited[nb.x][nb.y] = true;
			if(nb.x == end.x && nb.y == end.y) return ans;
			if(map[nb.x][nb.y] != 0 ) continue;
			Q.add(new Node(nb, 0, i));
		}
		while(!Q.isEmpty()){
			Node tmp = Q.remove();
			for(int i=0; i<4; i++){
				Spot nb = new Spot(tmp.s.x+dx[i], tmp.s.y+dy[i]);
				if(!inMap(nb)) continue;
				if(visited[nb.x][nb.y]) continue;
				visited[nb.x][nb.y] = true;
				if(nb.x == end.x && nb.y == end.y){
					if(tmp.corner == 2 && tmp.direction != i) continue;
					else {
						if(tmp.c1 != null) ans.corner1 = new Spot(tmp.c1);
						if(tmp.c2 != null) ans.corner2 = new Spot(tmp.c2);
						if(tmp.direction == i) return ans;
						else {
							if(tmp.corner == 0) ans.corner1 = new Spot(tmp.s);
							else if(tmp.corner == 1) ans.corner2 = new Spot(tmp.s);
							return ans;
						}
					}
				}
				if(map[nb.x][nb.y] != 0) continue;
				if(tmp.direction == i ) Q.add(new Node(nb, tmp.corner, i));
				else {
					if(tmp.corner<2) {
						Node ntmp = new Node(nb, tmp.corner+1, i);
						if(tmp.corner == 0) ntmp.c1 = new Spot(tmp.s);
						else {
							ntmp.c1 = new Spot(tmp.c1);
							ntmp.c2 = new Spot(tmp.s);
						}
						Q.add(ntmp);
					}
				}
			}
		}
		return null;
	}
	*/
	
	public Solution bfs(Spot start, Spot end){
		Solution ans = new Solution(map[end.x][end.y], start, end);
		Queue<Spot> Q = new LinkedList<Spot>();
		visited[start.x][start.y] = true;
		Q.add(start);
		Spot levele = start,tmpe = start; int level = 0;
		while(!Q.isEmpty()){
			Spot now = Q.remove();
			for(int i=0; i<4; i++){
				Spot nb = now;
				int xx = dx[i], yy = dy[i];
				while(true){
					nb = new Spot(nb.x+xx, nb.y+yy);
					if(!inMap(nb)) break;
					if(visited[nb.x][nb.y]) break;
					visited[nb.x][nb.y] = true;
					if(nb.x == end.x && nb.y == end.y) {
						//System.out.printf("%d\n",level);
						return ans;
					}
					if(map[nb.x][nb.y] != 0) break;
					Q.add(nb);
					tmpe = nb;
				}
			}
			if(now == levele) {
				level++;
				if(level == 3) break;
				levele = tmpe;
			}
		}
		return null;
	}
	
	public void Display(){//Repaint
	
	}
	
	public boolean isSame(Spot a,Spot b){
		return (map[a.x][a.y]==map[b.x][b.y]) && !(a.x == b.x && a.y == b.y);
	}
	
	public Solution isConnected(Spot begin,Spot end){
		if(begin == null || end == null) return null;
		//Solution tmp = Hints.search(begin, end);
		//if(tmp!= null) return tmp;
		begin.k = map[begin.x][begin.y];
		end.k = map[end.x][end.y];
		for(int i = 0; i<=nrow+1; i++)
			for(int j = 0; j<=ncol+1; j++){
				visited[i][j] = false;
			}
		//corners[0] = corners[1] = corners[2] = corners[3] = null;
		//System.out.printf("(%d, %d):%d    (%d,%d):%d   ", begin.x, begin.y, begin.k, end.x, end.y, end.k);
		/*//dfs
		if(dfs(begin, 0, 5, end) == true){
			//System.out.printf("connected!\n");
			Solution tmp = new Solution(map[end.x][end.y], begin, end);
			if(corners[0] != null) tmp.corner1 = corners[0];
			if(corners[1] != null) tmp.corner2 = corners[1];
			return tmp;
		}
		else {
			//System.out.printf("fail!\n");
			return null;
		}
		*/
		return bfs(begin, end);
	}
	
	/*public Solution FindOnePair(Spot begin,Spot end){
		return Solution
	}*/
	
	public Solution Clickon(Spot s){//Mouse Clicking on the spot s
		//System.out.printf("clickon:(%d,%d):%d \n", s.x, s.y, map[s.x][s.y] );
		if(marked==null){
			marked=s;
			return null;
		}
		else if(!isSame(marked,s)){
			marked=s;
			return null;
		}
		else {
			//System.out.printf("dfs:(%d,%d):%d \n", marked.x, marked.y, map[marked.x][marked.y]); 
			Solution tmp = isConnected(marked,s);
			if(tmp == null) marked=s;		
			else{
				cnt[tmp.kind] -=2;
				map[marked.x][marked.y] = map[s.x][s.y] = 0;
				marked=null;
				//Hints.delete(tmp);
				/*//for debug use
				System.out.printf("(%d,%d):%d - ", tmp.start.x, tmp.start.y, tmp.kind);
				if(tmp.corner1!=null) {
					System.out.printf("(%d,%d) - ", tmp.corner1.x, tmp.corner1.y);				
					if(tmp.corner2!=null) System.out.printf("(%d,%d) - ", tmp.corner2.x, tmp.corner2.y);
				}
				System.out.printf("(%d,%d)\n", tmp.end.x, tmp.end.y);*/				
				return tmp;
			}
		}
		return null;
	}
	
	public Spot getMarked(){
		return marked;
	}
	
	public Solution GiveHint(){
		return null;
	}
}