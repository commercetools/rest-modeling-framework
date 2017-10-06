<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObjectModel;

class MoneyModel extends JsonObjectModel implements Money {
    /**
     * @var string
     */
    private $centAmount;
    /**
     * @var string
     */
    private $currencyCode;

    /**
     * @return string
     */
    public function getCentAmount()
    {
        if (is_null($this->centAmount)) {
            $value = $this->raw(Money::FIELD_CENT_AMOUNT);
            $this->centAmount = (string)$value;
        }
        return $this->centAmount;
    }
    /**
     * @return string
     */
    public function getCurrencyCode()
    {
        if (is_null($this->currencyCode)) {
            $value = $this->raw(Money::FIELD_CURRENCY_CODE);
            $this->currencyCode = (string)$value;
        }
        return $this->currencyCode;
    }

    /**
     * @param string $centAmount
     * @return $this
     */
    public function setCentAmount($centAmount)
    {
        $this->centAmount = (string)$centAmount;

        return $this;
    }
    /**
     * @param string $currencyCode
     * @return $this
     */
    public function setCurrencyCode($currencyCode)
    {
        $this->currencyCode = (string)$currencyCode;

        return $this;
    }

}
