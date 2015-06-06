import java.util.LinkedList;
import java.util.Queue;

class Match{
	private int [][]map=new int[32][32];						// ��Ϸ����	
	private int []cnt=new int[100];								// ������ͼƬʣ�����
	private int nrow,ncol,nkind;								// ����������
	private int nres,nhint;
	private int rest;											// ͼ��ʣ��δ������
	private Spot marked = null;									// marked���ڼ�¼�û�֮ǰ���������ͼƬ
	private int dx[] = {1, 0, -1, 0}, dy[] = {0, 1, 0, -1};		// ������ - ����
	private boolean visited[][] = new boolean[32][32];			// ������ - �ѷ��ʼ�¼
	private SolutionList Hints = new SolutionList(30);			// ��ʾ�Լ���
	
	private boolean reshuffle = false;							// ˢ�±��
	
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

	public Match(int len){										// ��ʼ��
		nrow=ncol=len;					
		rest = nrow * ncol;
		nkind = rest / 8;										// ÿ��ͼƬ�Ķ�
		nres=2;nhint=5;
		int i;
		for (i=1;i<nkind;i++) cnt[i]=8;							// ��ʼʱÿ��ͼƬ����8��
		cnt[nkind] = nrow * ncol - 8 * (nkind - 1);				// ���һ�����ܿ�������
		for(i = 1; i <= nrow; i++)								// ���������ͼƬ��
			for(int j = 1; j<= ncol; j++)
				map[i][j] = 1;
		Reshuffle();											// ˢ������
	}
	
	public boolean getReshuffle(){								// ���⴫���Ƿ���Ҫˢ��
		return reshuffle;
	}
	
	public void resetReshuffle(){								// ˢ����ɺ����øñ��
		reshuffle = false;
	}
	
	public void Reshuffle(){									// ˢ������		
		int []tmp=new int[100];		
		int i,j,x;
		for (i=1;i<=nkind;i++) tmp[i]=cnt[i];					// ������ʱ����
		for (i=1;i<=nrow;i++)
			for (j=1;j<=ncol;j++)
				if(map[i][j]>0){
					do{
						x=(int)(nkind*Math.random())+1;			// �������ͼƬ��ȷ��������ͼƬ���໹��ʣ��
						//x = (int)(nkind*ThreadLocalRandom.current().nextDouble())+1;
					}while (tmp[x]==0);
					tmp[x]--;
					map[i][j]=x;
				}
		Hints.clear();										
		AddSolution();											// Ѱ�ҿ��н�
		//Hints.print();
		//Print();
	}
	
	// ���н��ά�������� ��ʼʱ����ȫ�̿��н⣬ÿ����������¿��н⼯�ϣ����н⼯��Ϊ��ʱ�ٽ���һ��ȫ�������� ȫ�������޽��ʱˢ�¡� 
	public void AddSolution(){									// ȫ��������ӿ��н�
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
	
	public int getMap(int i, int j){							// ����map[i][j]��ֵ
		return map[i][j];
	}
	
	public boolean inMap( Spot s ){								// �ж��Ƿ��������ڣ� ������
		if(s.x>=0 && s.y>=0 && s.x<=nrow+1 && s.y<=ncol + 1) return true;
		return false;
	}
	
	// �������ԣ�������ȣ� ��ÿ���㾡���ܵ����ĸ��������죬��һ�㼴��ʾ�յ㣬�������㼴��
	public void bfsSolution(Spot start){						// ��һ�����������п���������ԵĽ�
		Queue<Spot> Q = new LinkedList<Spot>();					// �ö��м�¼
		visited[start.x][start.y] = true;
		int k = map[start.x][start.y];							
		Q.add(start);											
		Spot levele = start,tmpe = start; 						// levele��¼��һ���ĩβ��㣬 tmpeΪ������������һ�����ʱ�����
		int level = 0;											// level��¼����������
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
						Hints.add(new Solution(start, nb));		// �ҵ�һ���ɴ����ͬ����ĵ㼴������Լ�����н⼯��
					}
					if(map[nb.x][nb.y] != 0) break;
					Q.add(nb);
					tmpe = nb;
				}
			}
			if(now == levele) {									// ����������һ���������һ�㣬���±�Ǹò�ĩβ
				level++;
				if(level == 3) break;
				levele = tmpe;
			}
		}
	}
	
	public Solution bfs(Spot start, Spot end){					// ����֪���������� �����Ƿ�Ϊ���жԣ��������̻���ͬ�ϡ��������򷵻�null
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
					if(nb.x == end.x && nb.y == end.y) {		// ���ѵ������㣬 ���ؼ���
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
	
	public boolean isSame(Spot a,Spot b){ 						// �ж������Ƿ�Ϊͬһ�����Ҳ���ͬһ��
		return (map[a.x][a.y]==map[b.x][b.y]) && !(a.x == b.x && a.y == b.y);
	}
	
	public Solution isConnected(Spot begin,Spot end){			// �ж������Ƿ������� ���򷵻ؽ���ԣ����򷵻�null
		if(begin == null || end == null) return null;
		//Solution tmp = Hints.search(begin, end);
		for(int i = 0; i<=nrow+1; i++)							// ����visited����
			for(int j = 0; j<=ncol+1; j++){
				visited[i][j] = false;
			}
		//System.out.printf("(%d, %d):%d    (%d,%d):%d   ", begin.x, begin.y, begin.k, end.x, end.y, end.k);
		return bfs(begin, end);
	}
	
	public Solution Clickon(Spot s){							// ����갴��s��ʱ�Ĳ���
		//System.out.printf("clickon:(%d,%d):%d \n", s.x, s.y, map[s.x][s.y] );
		if(!inMap(s)) return null;
		if(marked==null){										// ������һ���㣬���һ�ΰ������һ��������������
			marked=s;
			return null;
		}
		else if(!isSame(marked,s)){								// ����һ����ʱ�� ���������಻ͬ��϶���������
			marked=s;
			return null;
		}
		else {
			//System.out.printf("dfs:(%d,%d):%d \n", marked.x, marked.y, map[marked.x][marked.y]); 
			Solution tmp = isConnected(marked,s);
			if(tmp == null) marked=s;							// ��δ�������Ǹõ㣬�ȴ���һ�ΰ���
			else{
				cnt[map[s.x][s.y]] -=2;							// �������㣬������ͼƬ����2
				map[marked.x][marked.y] = map[s.x][s.y] = 0;	// ��Ӧ������Ϊ��
				marked=null;									// �ѱ�ǵ��ÿ�
				Hints.delete(tmp);								// �ӿ��н⼯����ɾ����ؿ��ж�
				rest -= 2;										// ������ʣ������2
				/*//for debug use
				System.out.printf("(%d,%d):%d - ", tmp.start.x, tmp.start.y, tmp.kind);
				if(tmp.corner1!=null) {
					System.out.printf("(%d,%d) - ", tmp.corner1.x, tmp.corner1.y);				
					if(tmp.corner2!=null) System.out.printf("(%d,%d) - ", tmp.corner2.x, tmp.corner2.y);
				}
				System.out.printf("(%d,%d)\n", tmp.end.x, tmp.end.y);*/
				//Hints.print();
				if(Hints.empty()){								// �����н⼯�Ͽ��ˣ� ���ٴ�ȫ���������������޽���ˢ������
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
	
	public boolean isEmpty(){									// �ж������Ƿ�ȫ��Ϊ�գ�����Ϸ�Ƿ����
		if (rest <= 0) return true;
		else return false;
	}
	
	public Spot getMarked(){
		return marked;
	}
	
	public Solution GiveHint(){									// ����ʾ�� �ӿ��н⼯�������һ�����ɣ������н⼯��Ϊ�գ�����ͬ��
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