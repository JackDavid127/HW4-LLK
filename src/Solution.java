class Solution{ 							// 记录结果对
	public Spot start, end;
	//public Spot corner1, corner2;
	//public int kind;
	public Solution(Spot s, Spot e){
		start = s; end = e;
	}
	
	public Solution(Solution s){
		start = new Spot(s.start);
		end = new Spot(s.end);
		//kind = s.kind;
	}
}
