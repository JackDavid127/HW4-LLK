public class SolutionList {
	private int size;
	private Solution list[];		
	private int fvacant;
	private int num = 0;
	
	public SolutionList(int s){
		list = new Solution[s];
		size = s;
	}
	
	public void print(){
		System.out.printf("Hints:\n");
		System.out.printf("num:%d\n", num);
		for(int i = 0; i<size; i++){
			if(list[i] != null) {
				System.out.printf("(%d,%d) - (%d,%d)\n", list[i].start.x, list[i].start.y, list[i].end.x, list[i].end.y);
			}
		}
	}
	
	public void add(Solution s){
		if (s == null) return;
		if (search(s.start, s.end)!=null) return;
		num++;
		if (fvacant < size && list[fvacant] == null){
			list[fvacant] = new Solution(s);
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
			if(list[i] == null) continue;
			if(list[i].start.equals(s.start) || list[i].end.equals(s.end) ||
					list[i].start.equals(s.end) || list[i].end.equals(s.start)){
				list[i] = null;
				num--;
			}
			
		}
	}
	
	public Solution search(Spot begin, Spot end){
		for(int i = 0; i<size; i++){
			if(list[i] == null){
				if(i<fvacant) fvacant = i;
				continue;
			}
			Solution tmp = list[i];
			if(tmp.start.equals(begin) && tmp.end.equals(end)) return tmp;
			if(tmp.start.equals(end) && tmp.end.equals(begin)) return tmp;
		}
		return null;
	}
	
	public Solution getOne(){
		for(int i = 0; i<size; i++)
			if(list[i]!=null){
				Solution tmp = new Solution(list[i]);
				//list[i] = null;
				return tmp;
			}
		return null;
	}
	
	public boolean empty(){
		if( num > 0 ) return false;
		return true;
	}
	
	public void clear(){
		for(int i = 0; i<size; i++) list[i] = null;
		num = 0;
	}
}
