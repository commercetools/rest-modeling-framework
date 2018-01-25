<?php
declare(strict_types = 1);
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
    protected $centAmount;
    /**
     * @var string
     */
    protected $currencyCode;

    /**
     * @return string
     */
    public function getCentAmount()
    {
        if (is_null($this->centAmount)) {
            $value = $this->raw(Money::FIELD_CENT_AMOUNT);
            $value = (string)$value;
            $this->centAmount = $value;
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
            $value = (string)$value;
            $this->currencyCode = $value;
        }
        return $this->currencyCode;
    }

    /**
     * @param string $centAmount
     * @return $this
     */
    public function setCentAmount(string $centAmount)
    {
        $this->centAmount = (string)$centAmount;

        return $this;
    }
    /**
     * @param string $currencyCode
     * @return $this
     */
    public function setCurrencyCode(string $currencyCode)
    {
        $this->currencyCode = (string)$currencyCode;

        return $this;
    }

}
