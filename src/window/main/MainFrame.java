package window.main;

import constant.Const;
import events.BombsCountListener;
import events.GameListener;
import window.champions.Records;
import window.champions.RecordsFrame;
import window.settings.SettingsFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>
 * Main window of program. Contains labels for game time and current bombs
 * count, button to restart game and {@link Field} object. Also contains menu.
 * </p>
 * <p/>
 * <p>
 * Implements {@link GameListener} and {@link BombsCountListener} to take
 * messages from {@link Field}. Contains timer to track time.
 * </p>
 *
 * @author Vlad
 */
public class MainFrame extends JFrame implements GameListener, BombsCountListener {

    private final JLabel lblTime = new JLabel();
    private final JLabel lblBombs = new JLabel();
    private JButton btnNewGame;

    private Field field;

    private Timer timer;
    private int secondsOfGame;

    private int fieldWidth = Const.EasyFieldWidth();
    private int fieldHeight = Const.EasyFieldHeight();
    private int bombs = Const.EasyBombsCount();

    private GameDifficulty difficulty;

    private final Records records;

    /**
     * <p>
     * Initiates frame, menu, fields. Creates {@link Field} object with default
     * difficulty level is {@link GameDifficulty#Easy}.
     * </p>
     *
     * @author Vlad
     */
    public MainFrame() {
        initFrameContent();
        initMenuBar();
        initActions();

        records = new Records();
        difficulty = GameDifficulty.Easy;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();

        int centerX = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds().getCenterX();
        int centerY = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds().getCenterY();
        int windowX = centerX - getSize().width / 2;
        int windowY = centerY - getSize().height / 2;
        setLocation(windowX, windowY);

        setVisible(true);
    }

    /**
     * <p>
     * Restart game with a new parameters. If parameters are equals to ones of
     * any difficulty level set this level and set Special level otherwise.
     * </p>
     *
     * @param width  - new field width
     * @param height - new field height
     * @param bombs  - new amount of bombs
     * @author Vlad
     */
    public void changeSettings(int width, int height, int bombs) {
        fieldWidth = width;
        fieldHeight = height;
        this.bombs = bombs;

        if (width == Const.EasyFieldWidth()
                && height == Const.EasyFieldHeight()
                && bombs == Const.EasyBombsCount()) {
            difficulty = GameDifficulty.Easy;
        } else if (width == Const.MediumFieldWidth()
                && height == Const.MediumFieldHeight()
                && bombs == Const.MediumBombsCount()) {
            difficulty = GameDifficulty.Medium;
        } else if (width == Const.HardFieldWidth()
                && height == Const.HardFieldHeight()
                && bombs == Const.HardBombsCount()) {
            difficulty = GameDifficulty.Hard;
        } else {
            difficulty = GameDifficulty.Special;
        }

        restart();
    }

    @Override
    public void endOfGame(boolean win) {
        timer.stop();
        if (win) {
            if (difficulty != GameDifficulty.Special) {
                String name;
                if (records.canAdd(difficulty, secondsOfGame)) {
                    name = JOptionPane.showInputDialog(this, "Type your name to carry it in the high score");
                    if (!name.isEmpty()) {
                        records.addRecord(difficulty, name, secondsOfGame);
                        invokeRecordsFrame(difficulty);
                    }
                }
            }
        } else {
            btnNewGame.setIcon(new ImageIcon(Const.NegativeSmileIcon()));
        }
    }

    @Override
    public void startGame() {
        secondsOfGame = 0;
        timer.start();
    }

    @Override
    public void updateBombsCount(int newCount) {
        lblBombs.setText(numberConvert(newCount));
    }

    private void initActions() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                lblTime.setText(numberConvert(secondsOfGame++));
            }
        });

        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
    }

    /**
     * <p>
     * Restarts game. Reinitiate object fields and command {@link Field} object
     * to restart with new parameters.
     * </p>
     *
     * @author Vlad
     */
    private void restart() {
        timer.stop();
        lblTime.setText(numberConvert(0));
        lblBombs.setText(numberConvert(bombs));
        field.restart(fieldWidth, fieldHeight, bombs);
        btnNewGame.setIcon(new ImageIcon(Const.PositiveSmileIcon()));
        pack();
        repaint();
    }

    /**
     * <p>
     * Represents number as a string in of the form "005", "012", "621". Minimal
     * number of digits specified by {@link Const#MinDigitsAtLabel()} constant.
     * </p>
     *
     * @param number
     * @return string with number
     * @author Vlad
     */
    private String numberConvert(int number) {
        StringBuilder num = new StringBuilder(String.valueOf(number));
        if (num.length() < Const.MinDigitsAtLabel()) {
            num.insert(0, fillChar('0', Const.MinDigitsAtLabel() - num.length()));
        }

        return num.toString();
    }

    /**
     * <p>
     * Fill string with specified char.
     * </p>
     *
     * @param ch
     * @param amount
     * @return string with <b>amount</b> of <b>ch</b>
     * @author Vlad
     */
    private String fillChar(char ch, int amount) {
        StringBuilder result = new StringBuilder(amount);
        for (int i = 0; i < amount; ++i) {
            result.append(ch);
        }

        return result.toString();
    }

    /**
     * <p>
     * Creates UI.
     * </p>
     *
     * @author Vlad
     */
    private void initFrameContent() {
        field = new Field(fieldWidth, fieldHeight, bombs);
        field.setGameListener(this);
        field.setBombsCountListener(this);
        field.setBorder(new LineBorder(new Color(0, 0, 0)));
        getContentPane().add(field, BorderLayout.CENTER);

        JPanel pnlHeader = new JPanel();
        pnlHeader.setBorder(new LineBorder(new Color(0, 0, 0)));
        getContentPane().add(pnlHeader, BorderLayout.NORTH);
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.X_AXIS));

        Component horizontalStrut = Box.createHorizontalStrut(10);
        pnlHeader.add(horizontalStrut);

        lblTime.setText(numberConvert(0));
        lblTime.setOpaque(true);
        lblTime.setBackground(Const.LabelBackground());
        lblTime.setForeground(Const.LabelForeground());
        lblTime.setFont(Const.LabelFont());
        pnlHeader.add(lblTime);

        Component horizontalGlue = Box.createHorizontalGlue();
        pnlHeader.add(horizontalGlue);

        btnNewGame = new JButton("");
        btnNewGame.setPreferredSize(new Dimension(32, 32));
        btnNewGame.setOpaque(false);
        btnNewGame.setBackground(SystemColor.controlHighlight);
        btnNewGame.setForeground(SystemColor.controlHighlight);
        btnNewGame.setMaximumSize(new Dimension(32, 32));
        btnNewGame.setIcon(new ImageIcon(Const.PositiveSmileIcon()));
        pnlHeader.add(btnNewGame);

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        pnlHeader.add(horizontalGlue_1);

        lblBombs.setText(numberConvert(Const.EasyBombsCount()));
        lblBombs.setOpaque(true);
        lblBombs.setBackground(Const.LabelBackground());
        lblBombs.setForeground(Const.LabelForeground());
        lblBombs.setFont(Const.LabelFont());
        pnlHeader.add(lblBombs);

        Component horizontalStrut_1 = Box.createHorizontalStrut(10);
        pnlHeader.add(horizontalStrut_1);
    }

    /**
     * <p>
     * Creates menu bar and initiates it with actions.
     * </p>
     *
     * @author Vlad
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnGame = new JMenu("Game");
        menuBar.add(mnGame);

        JMenuItem mntmNewGame = new JMenuItem("New game");
        mntmNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        mnGame.add(mntmNewGame);

        mnGame.addSeparator();

        JMenuItem mntmEasy = new JMenuItem("Easy");
        mntmEasy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeSettings(Const.EasyFieldWidth(), Const.EasyFieldHeight(),
                        Const.EasyBombsCount());
                difficulty = GameDifficulty.Easy;
            }
        });
        mnGame.add(mntmEasy);

        JMenuItem mntmMedium = new JMenuItem("Medium");
        mntmMedium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeSettings(Const.MediumFieldWidth(), Const.MediumFieldHeight(),
                        Const.MediumBombsCount());
                difficulty = GameDifficulty.Medium;
            }
        });
        mnGame.add(mntmMedium);

        JMenuItem mntmHard = new JMenuItem("Hard");
        mntmHard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeSettings(Const.HardFieldWidth(), Const.HardFieldHeight(),
                        Const.HardBombsCount());
                difficulty = GameDifficulty.Hard;
            }
        });
        mnGame.add(mntmHard);

        JMenuItem mntmSpecial = new JMenuItem("Special");
        mntmSpecial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                invokeSettingsFrame();
                difficulty = GameDifficulty.Special;
            }
        });
        mnGame.add(mntmSpecial);

        mnGame.addSeparator();

        JMenuItem mntmRecords = new JMenuItem("Records");
        mntmRecords.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                invokeRecordsFrame(difficulty);
            }
        });
        mnGame.add(mntmRecords);

        mnGame.addSeparator();

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnGame.add(mntmExit);
    }

    /**
     * <p>
     * Invokes RecordsFrame.
     * </p>
     *
     * @param defaultList difficulty that will be selected by default in records frame.
     * @author Vlad
     */
    private void invokeRecordsFrame(GameDifficulty defaultList) {
        RecordsFrame recordsFrame = new RecordsFrame(records, defaultList);
        recordsFrame.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
    }

    /**
     * <p>
     * Invokes SettingsFrame.
     * </p>
     *
     * @author Vlad
     */
    private void invokeSettingsFrame() {
        SettingsFrame settings = new SettingsFrame(this, fieldWidth, fieldHeight, bombs);
        settings.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
    }
}
