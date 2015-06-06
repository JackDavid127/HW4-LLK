public class SolutionList {								// 可行解集合
	private int size;
	private Solution list[];							// 简单采用数组存放
	private int fvacant;								// 简单记录第一个空位
	private int num = 0;								// 记录集合中解的个数
	
	public SolutionList(int s){
		list = new Solution[s];
		size = s;
	}
	
	public void print(){								// 输出可行解集合，调试用
		System.out.printf("Hints:\n");
		System.out.printf("num:%d\n", num);
		for(int i = 0; i<size; i++){
			if(list[i] != null) {
				System.out.printf("(%d,%d) - (%d,%d)\n", list[i].start.x, list[i].start.y, list[i].end.x, list[i].end.y);
			}
		}
	}
	
	public void add(Solution s){						//往集合中添加解
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
	
	public void delete(Solution s){						// 往集合中删除所有与结果对相关的解
		for(int i = 0; i<size; i++){
			if(list[i] == null) continue;
			if(list[i].start.equals(s.start) || list[i].end.equals(s.end) ||
					list[i].start.equals(s.end) || list[i].end.equals(s.start)){
				list[i] = null;
				num--;
			}
			
		}
	}
	
	public Solution search(Spot begin, Spot end){       // 搜索是否已有结果
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
	
	public Solution getOne(){							// 返回一个结果对
		for(int i = 0; i<size; i++)
			if(list[i]!=null){
				Solution tmp = new Solution(list[i]);
				//list[i] = null;
				return tmp;
			}
		return null;
	}
	
	public boolean empty(){								// 判断可行解是否为空
		if( num > 0 ) return false;
		return true;
	}
	
	public void clear(){								// 清空集合
		for(int i = 0; i<size; i++) list[i] = null;
		num = 0;
	}
}
