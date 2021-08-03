package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="watchlists")
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchlist_id")
    private Integer watchlistId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            foreignKey=@ForeignKey(name = "FK_USER_WATCHLIST"))
    private UserEntity user;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name="watchlist_symbol",
    joinColumns = @JoinColumn(
            name="watchlist_id",
            foreignKey=@ForeignKey(name = "FK_SYMBOL_WATCHLIST")),
    inverseJoinColumns = @JoinColumn(
            name="symbol_id",
            foreignKey=@ForeignKey(name = "FK_WATCHLIST_SYMBOL"))
    )
    private Set<Symbol> symbols = new HashSet<>();

    @Column(nullable = false)
    private String name;

    public void addSymbols(Symbol symbol){
        this.symbols.add(symbol);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((watchlistId == null) ? 0 : watchlistId.hashCode());
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
        WatchList other = (WatchList) obj;
        if (watchlistId == null) {
            if (other.watchlistId != null)
                return false;
        } else if (!watchlistId.equals(other.watchlistId))
            return false;
        return true;
    }


}
