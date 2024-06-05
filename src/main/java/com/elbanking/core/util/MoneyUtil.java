package com.elbanking.core.util;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class MoneyUtil {
    public static Money getMoneyFromCent(Long cent,String currency){
        CurrencyUnit currencyUnit = Monetary.getCurrency(currency);
        return Money.ofMinor(currencyUnit,cent);
    }
}
