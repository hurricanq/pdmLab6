import javax.swing.*;

public class frmMain extends JFrame {
    private static frmMain instance;
    private JPanel panel;
    private JTextField txtStudent;
    private JButton btnQuery;
    private JButton btnAddStudent;
    private JButton btnDelete;
    private JTextField txtSubject;
    private JTextField txtTeacher;
    private JButton btnAddSubject;
    private JTextField txtDate;
    private JButton btnAddLesson;
    private JLabel lblApp;
    private JLabel lblStudent;
    private JLabel lblSubject;
    private JLabel lblTeacher;
    private JLabel lblDate;
    private JTextField txtQuery;
    private JButton btnExecute;

    private frmMain() {
        setContentPane(panel);
        setTitle("PDM - Lab 6");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
        btnExecute.addActionListener(
                e -> {
                    btnExecute.setEnabled(false);

                    if (txtQuery.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Empty query",
                                "Alert", JOptionPane.WARNING_MESSAGE);
                        btnExecute.setEnabled(true);
                        return;
                    }

                    SwingWorker<Void, Void> worker =
                            new SwingWorker<>() {
                                @Override
                                protected Void doInBackground() {
                                    ConnectSQL.showQuery(txtQuery.getText(), tblResult);
                                    return null;
                                }

                                @Override
                                protected void done() {
                                    btnExecute.setEnabled(true);
                                }
                            };
                    worker.execute();
                });
         */

        btnQuery.addActionListener(
                e -> {
                    if (txtStudent.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Student's name is empty!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String query = txtStudent.getText();
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "Confirm?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        btnQuery.setEnabled(false);
                        SwingWorker<Void, Void> worker =
                                new SwingWorker<>() {
                                    @Override
                                    protected Void doInBackground() {
                                        if (ConnectSQL.showQuery(txtStudent.getText()) != null) {
                                            txtStudent.setText("");
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Successful!",
                                                    "Success",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Unsuccessful!",
                                                    "Warning",
                                                    JOptionPane.WARNING_MESSAGE);
                                            txtStudent.setText("");
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btnQuery.setEnabled(true);
                                    }
                                };
                        worker.execute();
                    }
                });

        /*
        btnAddStudent.addActionListener(
                e -> {
                    if (txtStudent.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Student's name is empty!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String studentName = txtStudent.getText();
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "Confirm?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        btnAddStudent.setEnabled(false);
                        SwingWorker<Void, Void> worker =
                                new SwingWorker<>() {
                                    @Override
                                    protected Void doInBackground() {
                                        if (ConnectSQL.showQuery(txtStudent.getText()) != null) {
                                            ConnectSQL.insertStudent(studentName); // is this right?
                                            txtStudent.setText("");
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Successful!",
                                                    "Success",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Unsuccessful!",
                                                    "Warning",
                                                    JOptionPane.WARNING_MESSAGE);
                                            txtStudent.setText("");
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btnAddStudent.setEnabled(true);
                                    }
                                };
                        worker.execute();
                    }
                });
         */

        btnAddStudent.addActionListener(
                e -> {
                    if (txtStudent.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please enter the student's name.",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String studentName = txtStudent.getText();

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            ConnectSQL.insertStudent(studentName);
                            return null;
                        }

                        @Override
                        protected void done() {
                            try {
                                get();
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Student successfully added!",
                                        "Successful Query",
                                        JOptionPane.INFORMATION_MESSAGE);
                                txtStudent.setText("");
                                btnAddStudent.setEnabled(true); // Enable the button after the task is completed
                            } catch (Exception e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(
                                        null,
                                        "An error occurred while adding the student.",
                                        "Query Error",
                                        JOptionPane.ERROR_MESSAGE);
                                btnAddStudent.setEnabled(true); // Enable the button in case of an error
                            }
                        }
                    };

                    worker.execute();
                });
    }

    public static synchronized frmMain getInstance() {
        if (instance == null) {
            instance = new frmMain();
        }
        return instance;
    }
}
