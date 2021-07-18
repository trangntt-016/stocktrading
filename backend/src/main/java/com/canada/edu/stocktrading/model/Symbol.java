package com.canada.edu.stocktrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="symbols")
public class Symbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "symbol_id")
    private Integer symbolId;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Column(nullable = false)
    private String name; // fullname

    @ManyToMany(mappedBy = "symbols")
    @JsonIgnore
    private Set<Watchlist> watchlists = new HashSet<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((symbolId == null) ? 0 : symbolId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Symbol other = (Symbol) obj;
        if (symbolId == null) {
            if (other.symbolId != null)
                return false;
        } else if (!symbolId.equals(other.symbolId))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return symbolId+" symbol - "+name;
    }


}
