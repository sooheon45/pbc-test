package untitled.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import untitled.domain.*;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository
    extends PagingAndSortingRepository<Product, Long> {}
