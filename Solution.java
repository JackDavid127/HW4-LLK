class Solution{
	public Spot start, end;
	public Spot corner1, corner2;
	public int kind;
	public Solution(int k, Spot s, Spot e, Spot c1, Spot c2){
		kind = k;
		start = s;  end = e;
		corner1 = c1; corner2 = c2;
	}
	public Solution(int k, Spot s, Spot e){
		kind = k;
		start = s; end = e;
	}
	public Solution(int k, Spot s, Spot e, Spot c1){
		kind = k;
		start = s; end = e; corner1 = c1;
	}
	public Solution(Solution s){
		start = new Spot(s.start);
		end = new Spot(s.end);
		kind = s.kind;
	}
}
