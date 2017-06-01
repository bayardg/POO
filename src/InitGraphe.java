public class InitGraphe {
    static public void init(Robot[] robots, Carte m) {
        CalculGraphe graphedrone = new CalculGraphe(m,TypeRobot.DRONE);
        CalculGraphe grapheroues= new CalculGraphe(m,TypeRobot.ROUES);
        CalculGraphe graphepattes= new CalculGraphe(m,TypeRobot.PATTES);
        CalculGraphe graphechenilles= new CalculGraphe(m,TypeRobot.CHENILLES);
        for (Robot Walle : robots) {
            switch (Walle.getTypeRobot()) {
                case DRONE:
                    Walle.setGraphe(graphedrone);
                    break;
                case CHENILLES:
                    Walle.setGraphe(graphechenilles);
                    break;
                case ROUES:
                    Walle.setGraphe(grapheroues);
                    break;
                case PATTES:
                    Walle.setGraphe(graphepattes);
                    break;
            }
        }
    }
}
