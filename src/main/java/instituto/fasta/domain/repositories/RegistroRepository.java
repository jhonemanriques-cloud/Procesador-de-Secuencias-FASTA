package instituto.fasta.domain.repositories;

import instituto.fasta.domain.models.RegistroGenomico;

public interface RegistroRepository {
    /**
     * Guarda el registro en la base de datos y retorna el mismo objeto
     * con el ID generado asignado.
     */
    RegistroGenomico guardar(RegistroGenomico registro) throws Exception;
}