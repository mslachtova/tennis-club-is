package io.mslachtova.service;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Interface for bean mapper
 *
 * @author Monika Slachtova
 */
@Service
public interface BeanMapper {
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
    <T> T mapTo(Object u, Class<T> mapToClass);
    Mapper getMapper();
}
