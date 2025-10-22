import java.util.List;

class BinarySearch {
    List<Integer> items;
    BinarySearch(List<Integer> items) {
        this.items=items;
    }

    int indexOf(int item) throws ValueNotFoundException {
        if(this.items.isEmpty()) {
            throw new ValueNotFoundException("Value not in array");
        }
        return binarySearch(item,0,items.size()-1);
    }

    int binarySearch(int target, int left, int right) throws ValueNotFoundException{
        if(left>right) {
            throw new ValueNotFoundException("Value not in array");
        }
        int middle = (left+right)/2;
        int value = items.get(middle);
        if(value == target) return middle;
        if(value>target) {
            return binarySearch(target,left,middle-1);
        }
        return binarySearch(target,middle+1,right);
    }
}
