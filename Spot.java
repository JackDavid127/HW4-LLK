class Spot{
	public int x,y,k;
	public Spot() {}
	public Spot(int a,int b){
		x=a;y=b;
	}
	public Spot(int a, int b, int k_){
		x = a; y = b; k = k_;
	}
	public Spot(Spot s){
		if (s == null) {
			return;
		}
		x = s.x;
		y = s.y;
		k = s.k;
	}
	public boolean equals(Spot s){
		if(x == s.x && y == s.y) return true;
		return false;
	}
}
