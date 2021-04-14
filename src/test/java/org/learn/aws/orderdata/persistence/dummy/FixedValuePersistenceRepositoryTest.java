package org.learn.aws.orderdata.persistence.dummy;

import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FixedValuePersistenceRepositoryTest {

    @Test
    public void shouldSearchDummyOrder() {
        PersistenceRepository fixedValueRepository = new FixedValuePersistenceRepository();
        Order searchedOrder = fixedValueRepository.searchOrder("AA");
        assertThat(searchedOrder.getOrderNumber(), is("AA"));
        assertThat(searchedOrder.getProduct(), is("AWS"));
    }

}