package Repository;

import Domain.Auditorium;

public class AuditoriumFileRepository extends FileRepository<Auditorium> {
    public AuditoriumFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String serialize(Auditorium obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getRows() + "," + obj.getCols();
    }

    @Override
    protected Auditorium deserialize(String data) {
        String[] objectParts = data.split(",");


        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int rows = Integer.parseInt(objectParts[2]);
        int cols = Integer.parseInt(objectParts[3]);

        return new Auditorium(id, name, rows, cols);

    }
}
