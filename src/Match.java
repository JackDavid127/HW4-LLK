import java.util.LinkedList;
import java.util.Queue;

class Match{
	private int [][]map=new int[32][32];						// 游戏棋盘	
	private int []cnt=new int[100];								// 各种类图片剩余计数
	private int nrow,ncol,nkind;								// 长、宽、种类
	private int nres,nhint;
	private int rest;											// 图中剩余未消块数
	private Spot marked = null;									// marked用于记录用户之前点击的其他图片
	private int dx[] = {1, 0, -1, 0}, dy[] = {0, 1, 0, -1};		// 搜索用 - 方向
	private boolean visited[][] = new boolean[32][32];			// 搜索用 - 已访问记录
	private SolutionList Hints = new SolutionList(30);			// 提示对集合
	
	private boolean reshuffle = false;							// 刷新标记
	
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

	public Match(int len){										// 初始化
		nrow=ncol=len;					
		rest = nrow * ncol;
		nkind = rest / 8;										// 每种图片四对
		nres=2;nhint=5;
		int i;
		for (i=1;i<nkind;i++) cnt[i]=8;							// 初始时每种图片都有8块
		cnt[nkind] = nrow * ncol - 8 * (nkind - 1);				// 最后一种依总块数而定
		for(i = 1; i <= nrow; i++)								// 标记棋盘有图片处
			for(int j = 1; j<= ncol; j++)
				map[i][j] = 1;
		Reshuffle();											// 刷新棋盘
	}
	
	public boolean getReshuffle(){								// 向外传递是否需要刷新
		return reshuffle;
	}
	
	public void resetReshuffle(){								// 刷新完成后重置该标记
		reshuffle = false;
	}
	
	public void Reshuffle(){									// 刷新棋盘		
		int []tmp=new int[100];		
		int i,j,x;
		for (i=1;i<=nkind;i++) tmp[i]=cnt[i];					// 种类临时计数
		for (i=1;i<=nrow;i++)
			for (j=1;j<=ncol;j++)
				if(map[i][j]>0){
					do{
						x=(int)(nkind*Math.random())+1;			// 随机分配图片，确保所分配图片种类还有剩余
						//x = (int)(nkind*ThreadLocalRandom.current().nextDouble())+1;
					}while (tmp[x]==0);
					tmp[x]--;
					map[i][j]=x;
				}
		Hints.clear();										
		AddSolution();											// 寻找可行解
		//Hints.print();
		//Print();
	}
	
	// 可行解的维护方案： 初始时搜索全盘可行解，每次消除后更新可行解集合，可行解集合为空时再进行一次全盘搜索， 全盘搜索无结果时刷新。 
	public void AddSolution(){									// 全盘搜索添加可行解
		for(int i = 1; i<nrow; i++)
			for(int j = 1; j<ncol; j++){
				if (map[i][j] == 0) continue;
				for(int p = 0; p<= nrow+1; p++)
					for(int q = 0; q<=ncol+1; q++)
						visited[p][q] = false;
				bfsSolution(new Spot(i, j));
				//if (tmp != null) Hints.add(tmp);
			}
	}
	
	public int getMap(int i, int j){							// 返回map[i][j]的值
		return map[i][j];
	}
	
	public boolean inMap( Spot s ){								// 判断是否在棋盘内， 搜索用
		if(s.x>=0 && s.y>=0 && s.x<=nrow+1 && s.y<=ncol + 1) return true;
		return false;
	}
	
	// 搜索策略：广度优先， 对每个点尽可能的向四个方向延伸，下一层即表示拐点，搜索两层即可
	public void bfsSolution(Spot start){						// 对一个点搜索所有可以与他配对的解
		Queue<Spot> Q = new LinkedList<Spot>();					// 用队列记录
		visited[start.x][start.y] = true;
		int k = map[start.x][start.y];							
		Q.add(start);											
		Spot levele = start,tmpe = start; 						// levele记录了一层的末尾结点， tmpe为搜索过程中下一层的临时最后结点
		int level = 0;											// level记录了搜索层数
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
					if(map[nb.x][nb.y] == k) {
						//System.out.printf("%d\n",level);
						Hints.add(new Solution(start, nb));		// 找到一个可达的相同种类的点即将结果对加入可行解集合
					}
					if(map[nb.x][nb.y] != 0) break;
					Q.add(nb);
					tmpe = nb;
				}
			}
			if(now == levele) {									// 若已搜索完一层则进入下一层，重新标记该层末尾
				level++;
				if(level == 3) break;
				levele = tmpe;
			}
		}
	}
	
	public Solution bfs(Spot start, Spot end){					// 对已知两点搜索， 看其是否为可行对，搜索过程基本同上。若不是则返回null
		Solution ans = new Solution(start, end);
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
					if(nb.x == end.x && nb.y == end.y) {		// 若搜到结束点， 返回即可
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
	
	public boolean isSame(Spot a,Spot b){ 						// 判断两点是否为同一类型且不是同一点
		return (map[a.x][a.y]==map[b.x][b.y]) && !(a.x == b.x && a.y == b.y);
	}
	
	public Solution isConnected(Spot begin,Spot end){			// 判断两点是否相连， 是则返回结果对，否则返回null
		if(begin == null || end == null) return null;
		//Solution tmp = Hints.search(begin, end);
		for(int i = 0; i<=nrow+1; i++)							// 重置visited数组
			for(int j = 0; j<=ncol+1; j++){
				visited[i][j] = false;
			}
		//System.out.printf("(%d, %d):%d    (%d,%d):%d   ", begin.x, begin.y, begin.k, end.x, end.y, end.k);
		return bfs(begin, end);
	}
	
	public Solution Clickon(Spot s){							// 当鼠标按在s上时的操作
		//System.out.printf("clickon:(%d,%d):%d \n", s.x, s.y, map[s.x][s.y] );
		if(!inMap(s)) return null;
		if(marked==null){										// 若无上一个点，如第一次按点或上一次消除了两个点
			marked=s;
			return null;
		}
		else if(!isSame(marked,s)){								// 有上一个点时， 若两点种类不同则肯定不能消除
			marked=s;
			return null;
		}
		else {
			//System.out.printf("dfs:(%d,%d):%d \n", marked.x, marked.y, map[marked.x][marked.y]); 
			Solution tmp = isConnected(marked,s);
			if(tmp == null) marked=s;							// 若未相连则标记该点，等待下一次按键
			else{
				cnt[map[s.x][s.y]] -=2;							// 消除两点，该种类图片减少2
				map[marked.x][marked.y] = map[s.x][s.y] = 0;	// 对应棋盘置为空
				marked=null;									// 已标记点置空
				Hints.delete(tmp);								// 从可行解集合中删除相关可行对
				rest -= 2;										// 棋盘上剩余点减少2
				/*//for debug use
				System.out.printf("(%d,%d):%d - ", tmp.start.x, tmp.start.y, tmp.kind);
				if(tmp.corner1!=null) {
					System.out.printf("(%d,%d) - ", tmp.corner1.x, tmp.corner1.y);				
					if(tmp.corner2!=null) System.out.printf("(%d,%d) - ", tmp.corner2.x, tmp.corner2.y);
				}
				System.out.printf("(%d,%d)\n", tmp.end.x, tmp.end.y);*/
				//Hints.print();
				if(Hints.empty()){								// 若可行解集合空了， 则再次全盘搜索，若搜索无解则刷新棋盘
					AddSolution();
					if(Hints.empty()){
						Reshuffle();
						reshuffle = true;
					}
				}
				return tmp;
			}
		}
		return null;
	}
	
	public boolean isEmpty(){									// 判断棋盘是否全部为空，即游戏是否结束
		if (rest <= 0) return true;
		else return false;
	}
	
	public Spot getMarked(){
		return marked;
	}
	
	public Solution GiveHint(){									// 给提示， 从可行解集合中输出一个即可，若可行解集合为空，处理同上
		Solution tmp = Hints.getOne();
		//Hints.print();
		if(tmp != null) return tmp;
		else{
			AddSolution();
			tmp = Hints.getOne();
			//Hints.print();
			if(tmp!=null) return tmp;
		}
		Reshuffle();
		return null;
	}
}