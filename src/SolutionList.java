public class SolutionList {
	private int size;
	private Solution list[];		
	private int fvacant;
	
	public SolutionList(int size){
		list = new Solution[size];
	}
	public void add(Solution s){
		if (search(s.start, s.end)!=null) return;
		if (list[fvacant] == null){
			list[fvacant] = s;
			fvacant = size;
			return;
		}
		for(int i = 0; i<size; i++)
			if(list[i] == null){
				list[i] = s;
			}
	}
	
	public void delete(Solution s){
		for(int i = 0; i<size; i++){
			if(list[i] == s) list[i] = null;
		}
	}
	
	public Solution search(Spot begin, Spot end){
		for(int i = 0; i<size; i++){
			if(list[i] == null){
				if(i<fvacant) fvacant = i;
				continue;
			}
			Solution tmp = list[i];
			if(begin.x == tmp.start.x && begin.y == tmp.start.y && 
					end.x == tmp.end.x && end.y == tmp.end.y) return tmp;
		}
		return null;
	}
	
	public boolean empty(){
		for(int i = 0; i<size; i++){
			if(list[i] != null) return false;
		}
		return true;
	}
}
