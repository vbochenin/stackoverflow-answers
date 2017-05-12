package stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Question43257795 {

    private List<Station> stations = ImmutableList.of(
            new Station(1L, ImmutableList.of(1L, 2L, 3L)),
            new Station(2L, ImmutableList.of(2L, 3L, 4L))
    );

    @Test
    public void test() {
        stations.stream()
                .flatMap(station -> station.getRadioList().stream().map(radio -> new ImmutablePair<>(radio, station)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (station, station2) -> station2));

        stations.stream()
                .map(s -> s.getRadioList().stream().collect(Collectors.toMap(b -> b, b -> s)))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assert.assertTrue(false);
    }


    private static class Station {
        private final Long id;

        private final List<Long> radioList;

        private Station(Long id, List<Long> radioList) {
            this.id = id;
            this.radioList = radioList;
        }


        public Long getId() {
            return id;
        }

        public List<Long> getRadioList() {
            return radioList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Station station = (Station) o;
            return Objects.equals(id, station.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
