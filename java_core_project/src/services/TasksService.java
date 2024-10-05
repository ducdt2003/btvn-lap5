package services;

import data.Database;
import entities.Tasks;
import entities.User;
import entities.Work;
import utils.Untils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TasksService implements IAddDeleteUpdate{
    private static TasksService tasksService;
    public static synchronized TasksService getInstance(){
        if (tasksService == null){
            tasksService = new TasksService();
        }
        return tasksService;
    }

    public Tasks inputTasks(Scanner sc){
        System.out.println("Nhập tên nhiệm vụ");
        String taskName = sc.nextLine();
        System.out.println("Nhập mô tả nhiệm vụ");
        String taskDescription = sc.nextLine();
        System.out.println("Nhập thời gian tạo nhiệm vụ");
        LocalDate addDate = Untils.convertStringToDate(sc, "dd/MM/yyyy");
        System.out.println("Nhập thời gian bắt đầu làm nhiệm vụ");
        LocalDate startDate = Untils.convertStringToDate(sc,"dd/MM/yyyy");
        System.out.println("Nhập thời gian kết thúc nhiệm vụ");
        LocalDate endDate = Untils.convertStringToDate(sc,"dd/MM/yyyy");
        System.out.println("Nhập thái thái");
        String status = sc.nextLine();
        return new Tasks(taskName, taskDescription, addDate, startDate, endDate, status);
    }




    public void addTaskByWorkId(Scanner sc) {
        System.out.println("Nhập ID công việc bạn muốn tìm kiếm:");
        int workId = Untils.inputInteger(sc); 

        Work work = findWorkById(workId); // Tìm công việc theo ID
        if (work != null) {
            Tasks tasks = inputTasks(sc);
            tasks.setWorkId(workId); 
            Database.tasks.add(tasks);
            System.out.println("Thêm nhiệm vụ thành công cho công việc ID: " + workId);
        } else {
            // Nếu không tìm thấy công việc, thông báo cho người dùng
            System.out.println("Không tìm thấy công việc với ID: " + workId);
        }
    }





    @Override
    public void remove(Scanner sc) {
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);

        if (work != null) {
            System.out.println("Danh sách nhiệm vụ thuộc công việc ID " + workId + ":");
            boolean foundTask = false;
            for (Tasks tasks : Database.tasks) {
                if (tasks.getWorkId() == workId) { // Kiểm tra ID công việc
                    System.out.println("ID Nhiệm Vụ: " + tasks.getId() + ", Tên Nhiệm Vụ: " + tasks.getTaskName());
                    foundTask = true; // Đánh dấu là đã tìm thấy ít nhất một nhiệm vụ
                }
            }
            if (!foundTask) {
                System.out.println("Không có nhiệm vụ nào cho công việc ID: " + workId);
                return; // Không có nhiệm vụ để xóa
            }

            System.out.println("Nhập ID nhiệm vụ muốn xóa:");
            int taskId = Untils.inputInteger(sc);
            Tasks taskToEdit = findTaskById(taskId);

            if (taskToEdit != null && taskToEdit.getWorkId() == workId) { // Kiểm tra nếu nhiệm vụ thuộc về công việc này
                Database.tasks.remove(taskToEdit); // Xóa nhiệm vụ
                System.out.println("Xóa thành công nhiệm vụ với ID: " + taskId);
            } else {
                System.out.println("Không tìm thấy nhiệm vụ hoặc nhiệm vụ không thuộc công việc ID: " + workId);
            }
        } else {
            System.out.println("Không tìm thấy công việc với ID: " + workId);
        }
    }

    @Override
    public void edit(Scanner sc) {
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);

        if (work != null) {
            System.out.println("Danh sách nhiệm vụ thuộc công việc ID " + workId + ":");
            boolean foundTask = false;
            for (Tasks tasks : Database.tasks) {
                if (tasks.getWorkId() == workId) { // Kiểm tra ID công việc
                    System.out.println("ID Nhiệm Vụ: " + tasks.getId() + ", Tên Nhiệm Vụ: " + tasks.getTaskName());
                    foundTask = true; // Đánh dấu là đã tìm thấy ít nhất một nhiệm vụ
                }
            }
            if (!foundTask) {
                System.out.println("Không có nhiệm vụ nào cho công việc ID: " + workId);
                return; // Không có nhiệm vụ để xóa
            }
            System.out.println("Nhập ID nhiệm vụ muốn sữa:");
            int taskId = Untils.inputInteger(sc);
            Tasks taskToEdit = findTaskById(taskId);
            if (taskToEdit != null && taskToEdit.getWorkId() == workId) { // Kiểm tra nếu nhiệm vụ thuộc về công việc này
                System.out.println("Nhập tên dự án mới (hiện tại: " + taskToEdit.getTaskName() + "):");
                String newTaskName = sc.nextLine();
                System.out.println("Nhập mô tả dự án mới (hiện tại: " + taskToEdit.getTaskDescription() + "):");
                String newTaskDescribe = sc.nextLine();
                System.out.println("Nhập thời gian khởi tạo dự (hiện tại" + taskToEdit.getAddDate() + "):");
                LocalDate nerAddDate = Untils.convertStringToDate(sc,"dd/MM/yyyy");
                System.out.println("Nhập thời gian bắt đầu mới (hiện tại: " + taskToEdit.getStartDate() + "):");
                LocalDate newStartDate = Untils.convertStringToDate(sc, "dd/MM/yyyy");
                System.out.println("Nhập thời gian kết thúc mới (hiện tại: " + taskToEdit.getEndDate() + "):");
                LocalDate newEndDate = Untils.convertStringToDate(sc, "dd/MM/yyyy");
                System.out.println("Nhập trạng thái mới (hiện tại: " + taskToEdit.getStatus()+ "):");
                String newStatus = sc.nextLine();
                taskToEdit.setTaskName(newTaskName);
                taskToEdit.setTaskDescription(newTaskDescribe);
                taskToEdit.setAddDate(nerAddDate);
                taskToEdit.setStartDate(newStartDate);
                taskToEdit.setEndDate(newEndDate);
                taskToEdit.setStatus(newStatus);
                System.out.println("Sữa thành công nhiệm vụ với ID: " + taskId);
            } else {
                System.out.println("Không tìm thấy nhiệm vụ hoặc nhiệm vụ không thuộc công việc ID: " + workId);
            }
        } else {
            System.out.println("Không tìm thấy công việc với ID: " + workId);
        }
    }

    @Override
    public void display(Scanner sc) {
       /* System.out.println("Nhập ID công việc bạn muốn Xem:");
        int task = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Tasks tasks = findTaskById(task);
        tasks.setWorkId(task);
        if (tasks != null){
            System.out.println("ID Công Việc: " + tasks.getWorkId());
            System.out.println("ID Nhiệm Vụ: " + tasks.getId());
            System.out.println("Tên Nhiệm Vụ: " + tasks.getTaskName());
            System.out.println("Ngày Tạo: " + tasks.getAddDate());
            System.out.println("Ngày Bắt Đầu: " + tasks.getStartDate());
            System.out.println("Ngày Kết Thúc: " + tasks.getEndDate());
            System.out.println("Trạng Thái: " + tasks.getStatus());
        }else {
            System.out.println("không tìm thấy công việc ");
        }*/
        if (Database.tasks.isEmpty()) {
            System.out.println("Không có nhiệm vụ nào trong danh sách.");
            return; // Nếu không có nhiệm vụ, kết thúc phương thức
        }

        System.out.println("Danh sách tất cả các nhiệm vụ:");
        for (Tasks tasks : Database.tasks) {
            System.out.println("ID Công Việc: " + tasks.getWorkId() +
                    ", ID Nhiệm Vụ: " + tasks.getId() +
                    ", Tên: " + tasks.getTaskName() +
                    ", Ngày Tạo: " + tasks.getAddDate() +
                    ", Ngày Bắt Đầu: " + tasks.getStartDate() +
                    ", Ngày Kết Thúc: " + tasks.getEndDate() +
                    ", Trạng Thái: " + tasks.getStatus());
        }
    }

    // tìm kiếm thoe id của công việc
    public void findById(Scanner sc){
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);
        if (work != null){
            System.out.println("Nhập id nhiệm vụ muốn tìm kiếm");
            int taskId = Untils.inputInteger(sc);
            Tasks tasks = findTaskById(taskId);
            if (tasks != null){
                tasks.displayTask();
            }else {
                System.out.println("không tìm thấy nhiệm vụ");
            }
        }else {
            System.out.println("không tìm thấy công việc");
        }
    }
    public void searchTaskByName(Scanner sc) {
        System.out.println("Nhập tên nhiệm vụ bạn muốn tìm kiếm:");
        String searchName = sc.nextLine(); // Nhập tên nhiệm vụ từ người dùng
        boolean found = false; // Biến để kiểm tra xem có tìm thấy nhiệm vụ hay không

        System.out.println("Danh sách nhiệm vụ tìm thấy:");
        for (Tasks task : Database.tasks) {
            // Kiểm tra xem tên nhiệm vụ có chứa chuỗi tìm kiếm không, không phân biệt chữ hoa chữ thường
            if (task.getTaskName().toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println("ID Công Việc: " + task.getWorkId() +
                        ", ID Nhiệm Vụ: " + task.getId() +
                        ", Tên: " + task.getTaskName() +
                        ", Ngày Tạo: " + task.getAddDate() +
                        ", Ngày Bắt Đầu: " + task.getStartDate() +
                        ", Ngày Kết Thúc: " + task.getEndDate() +
                        ", Trạng Thái: " + task.getStatus());
                found = true; // Đánh dấu là đã tìm thấy ít nhất một nhiệm vụ
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy nhiệm vụ nào với tên: " + searchName);
        }
    }


    // phân công nhiemj vụ cho nhân viên

    /*public void assignTaskToStaff(Scanner sc, User user, ArrayList<User> users) {
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);
        if (work != null){
            display(sc);
            System.out.println("Nhập id nhiệm vụ muốn phân công");
            int taskId = Untils.inputInteger(sc);
            Tasks tasks = findTaskById(taskId);
            if (tasks != null){
                // Nhập ID nhân viên để gán công việc
                System.out.println("Nhập ID nhân viên để gán công việc:");
                int employeeId = Untils.inputInteger(sc);

                // Kiểm tra xem ID nhân viên có tồn tại không
                boolean employeeExists = false;
                for (User userq : users) {
                    if (userq.getId() == employeeId) {
                        employeeExists = true; // Đánh dấu nhân viên tồn tại
                        break; // Thoát khỏi vòng lặp
                    }
                }
                // Gán công việc cho nhân viên nếu tồn tại
                if (employeeExists) {
                    tasks.setAssignee(employeeId); // Gán ID của nhân viên cho công việc
                    tasks.setStatus("Đã bàn giao cho nhân viên ID = " + employeeId); // Cập nhật trạng thái
                    System.out.println("Nhiệm vụ \"" + tasks.getTaskName() + "\" đã được gán cho nhân viên ID: " + employeeId);
                } else {
                    System.out.println("Không tìm thấy nhân viên với ID: " + employeeId);
                }
            }else {
                System.out.println("không tìm thấy nhiệm vụ");
            }
        }else {
            System.out.println("không tìm thấy công việc");
        }
    }
*/





    /*public void assignTaskToStaff(Scanner sc, User user, ArrayList<User> users) {
            // Nhập ID công việc
            System.out.println("Nhập ID công việc:");
            int workId = Untils.inputInteger(sc);
            Work work = findWorkById(workId);

        if (work == null) {
            System.out.println("Không tìm thấy công việc với ID: " + workId);
            return; // Kết thúc hàm nếu không tìm thấy công việc
        }

        // Hiển thị các nhiệm vụ liên quan đến công việc
        display(sc);

        // Nhập ID nhiệm vụ muốn phân công
        System.out.println("Nhập ID nhiệm vụ muốn phân công:");
        int taskId = Untils.inputInteger(sc);
        Tasks tasks = findTaskById(taskId);

        if (tasks == null) {
            System.out.println("Không tìm thấy nhiệm vụ với ID: " + taskId);
            return; // Kết thúc hàm nếu không tìm thấy nhiệm vụ
        }

        // Nhập ID nhân viên để gán công việc
        System.out.println("Nhập danh sách ID nhân viên để gán công việc (ngăn cách bằng dấu phẩy):");
        String employeeIdsInput = sc.nextLine();
        String[] employeeIds = employeeIdsInput.split(","); // Tách danh sách ID từ chuỗi nhập vào

        boolean anyEmployeeAssigned = false; // Biến kiểm tra xem có nhân viên nào được gán hay không

        for (String idStr : employeeIds) {
            int employeeId;
            try {
                employeeId = Integer.parseInt(idStr.trim()); // Chuyển đổi chuỗi sang số nguyên
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ: " + idStr);
                continue; // Bỏ qua ID không hợp lệ
            }

            // Kiểm tra xem ID nhân viên có tồn tại không
            User assignedEmployee = null;
            for (User userq : users) {
                if (userq.getId() == employeeId) {
                    assignedEmployee = userq; // Lưu nhân viên nếu tìm thấy
                    break; // Thoát khỏi vòng lặp
                }
            }

            // Gán công việc cho nhân viên nếu tồn tại
            if (assignedEmployee != null) {
                tasks.setAssignee(employeeId); // Gán ID của nhân viên cho nhiệm vụ
                tasks.setStatus("Đã bàn giao cho nhân viên ID = " + employeeId); // Cập nhật trạng thái
                System.out.println("Nhiệm vụ \"" + tasks.getTaskName() + "\" đã được gán cho nhân viên ID: " + employeeId);
                anyEmployeeAssigned = true; // Đánh dấu có nhân viên được gán
            } else {
                System.out.println("Không tìm thấy nhân viên với ID: " + employeeId);
            }
        }

        if (!anyEmployeeAssigned) {
            System.out.println("Không có nhân viên nào được gán cho nhiệm vụ.");
        }
    }
*/

    // Phương thức hiển thị danh sách nhiệm vụ cho một công việc
/*    public void findByAssignee(int assigneeId) {
        List<Tasks> assignedTasks = new ArrayList<>(); // Danh sách nhiệm vụ được giao cho assignee
        for (Tasks task : Database.tasks) {
            if (task.getAssignee() == assigneeId) {
                assignedTasks.add(task); // Thêm nhiệm vụ vào danh sách nếu assignee khớp
            }
            System.out.println(task);
        }

    }*/

    public void findByAssignee(int assigneeId) {
        ArrayList<Tasks> assignedTasks = new ArrayList<>(); // Danh sách nhiệm vụ được giao cho assignee

        // Duyệt qua danh sách các nhiệm vụ trong Database
        for (Tasks task : Database.tasks) {
            if (task.getAssignee() == assigneeId) {
                assignedTasks.add(task); // Thêm nhiệm vụ vào danh sách nếu assignee khớp
            }
        }

        // Hiển thị các nhiệm vụ được giao cho assignee
        if (assignedTasks.isEmpty()) {
            System.out.println("Không có nhiệm vụ nào được giao cho ID " + assigneeId);
        } else {
            System.out.println("Nhiệm vụ được giao cho người có ID " + assigneeId + ":");
            for (Tasks task : assignedTasks) {
                task.displayTask();
            }
        }
    }





    // tìm kiếm nhệm vụ theo id
    public Tasks findTaskById(int id) {
        for (Tasks tasks : Database.tasks) {
            if (tasks.getId() == id) {
                return tasks; // Trả về nhiệm vụ nếu tìm thấy
            }
        }
        return null; // Trả về null nếu không tìm thấy nhiệm vụ
    }

    // Tìm kiếm công việc theo ID
    public Work findWorkById(int workId) {
        for (Map.Entry<Integer, ArrayList<Work>> entry : Database.projectWorks.entrySet()) { // Lặp qua tất cả các dự án
            ArrayList<Work> works = entry.getValue(); // Danh sách công việc trong dự án
            for (Work work : works) {
                if (work.getId() == workId) {
                    return work; // Trả về công việc nếu tìm thấy
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy công việc
    }
}
