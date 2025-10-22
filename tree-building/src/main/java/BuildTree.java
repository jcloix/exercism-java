import java.util.*;
import java.util.stream.Collectors;

class BuildTree {

    TreeNode buildTree(ArrayList<Record> records) throws InvalidRecordsException {
        records.sort(Comparator.comparing(Record::getRecordId));
        validate(records);
        Map<Integer,TreeNode> treeNodeMap = records.stream()
                .collect(Collectors.toMap(Record::getRecordId, q->new TreeNode(q.getRecordId())));

        Map<Integer,List<Record>> parentMap = records.stream().filter(q->q.getParentId()!=0 || q.getRecordId() != 0)
                .collect(Collectors.groupingBy(Record::getParentId));
        List<TreeNode> treeNodes = new ArrayList<>();
        for(Record record : records) {
            TreeNode treeNode = treeNodeMap.get(record.getRecordId());
            if(parentMap.containsKey(record.getRecordId())) {
                treeNode.getChildren().addAll(parentMap.get(record.getRecordId())
                        .stream()
                        .map(q -> treeNodeMap.get(q.getRecordId())).toList());
            }
            treeNodes.add(treeNode);

        }
        if (!treeNodes.isEmpty()) {
            return treeNodes.getFirst();
        }

        return null;
    }

    void validate(ArrayList<Record> records) throws InvalidRecordsException {
        if (!records.isEmpty()) {
            if (records.getLast().getRecordId() != records.size() - 1) {
                throw new InvalidRecordsException("Invalid Records");
            }
            if (records.getFirst().getRecordId() != 0) {
                throw new InvalidRecordsException("Invalid Records");
            }
        }
        for (Record record : records) {
            if (record.getRecordId() == 0 && record.getParentId() != 0) {
                throw new InvalidRecordsException("Invalid Records");
            }
            if (record.getRecordId() < record.getParentId()) {
                throw new InvalidRecordsException("Invalid Records");
            }
            if (record.getRecordId() == record.getParentId() && record.getRecordId() != 0) {
                throw new InvalidRecordsException("Invalid Records");
            }
        }
    }

}
