package infinityshopping.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link infinityshopping.domain.Proba} entity. This class is used
 * in {@link infinityshopping.web.rest.ProbaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /probas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProbaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter naziv;

    private Boolean distinct;

    public ProbaCriteria() {}

    public ProbaCriteria(ProbaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.naziv = other.naziv == null ? null : other.naziv.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProbaCriteria copy() {
        return new ProbaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNaziv() {
        return naziv;
    }

    public StringFilter naziv() {
        if (naziv == null) {
            naziv = new StringFilter();
        }
        return naziv;
    }

    public void setNaziv(StringFilter naziv) {
        this.naziv = naziv;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProbaCriteria that = (ProbaCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(naziv, that.naziv) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naziv, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProbaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (naziv != null ? "naziv=" + naziv + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
